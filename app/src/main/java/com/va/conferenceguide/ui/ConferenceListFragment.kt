package com.va.conferenceguide.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.skydoves.landscapist.glide.GlideImage
import com.va.conferenceguide.BuildConfig
import com.va.conferenceguide.R
import com.va.conferenceguide.data.models.ConferenceListResult
import com.va.conferenceguide.data.models.Data
import com.va.conferenceguide.data.networking.Resource

class ConferenceListFragment : Fragment() {

    private val viewModel: ConferenceListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData(viewModel = viewModel, isPullToRefresh = false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val buildType = BuildConfig.BUILD_TYPE
                MaterialTheme {
                    ConferenceListLayout(
                        viewModel = viewModel,
                        buildType = buildType,
                        navController = findNavController()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ConferenceListLayout(
    viewModel: ConferenceListViewModel, buildType: String,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildType.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->

        val isRefreshing = viewModel.isRefreshing.value

        val pullRefreshState = rememberPullRefreshState(isRefreshing, {
            loadData(viewModel = viewModel, isPullToRefresh = true)
        })

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            val (pullRefresh) = createRefs()

            Column(modifier = Modifier.fillMaxSize()) {
                when (val conferenceListResult = viewModel.conferenceListResult.value) {
                    is Resource.Loading -> {
                        val loadingMessage = stringResource(
                            id = if (isRefreshing)
                                R.string.refreshing
                            else
                                R.string.loading
                        )
                        LoadingLayout(loadingMessage = loadingMessage)
                    }
                    is Resource.Success -> {
                        SuccessLayout(conferenceListResult.data, paddingValues, navController)
                    }
                    is Resource.Error -> {
                        ErrorLayout(conferenceListResult.error) {
                            viewModel.loadData(isRefresh = false)
                        }
                    }
                    else -> {}
                }
            }

            // added pull to refresh here
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.constrainAs(pullRefresh) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
            )
        }
    }
}

private fun loadData(viewModel: ConferenceListViewModel, isPullToRefresh: Boolean) {
    viewModel.loadData(isRefresh = isPullToRefresh)
}

@Composable
private fun SuccessLayout(
    data: ConferenceListResult,
    paddingValues: PaddingValues,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        val list = data.data
        val size = list.size
        items(size) { index ->
            val item = list[index]
            ConferenceListItem(conferenceItem = item) {
                navController.navigate(
                    ConferenceListFragmentDirections.actionConferenceListFragmentToConferenceDetailFragment(
                        data = item
                    )
                )
            }
            if (index != size - 1) {
                Divider()
            }
        }
    }
}

@Composable
private fun LoadingLayout(loadingMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = loadingMessage,
            style = MaterialTheme.typography.h6.copy(
                color = Color.DarkGray
            )
        )
    }
}

@Composable
private fun ErrorLayout(error: String, retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { retry() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
private fun ConferenceListItem(conferenceItem: Data, onClick: (Data) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(conferenceItem)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        GlideImage(
            imageModel = { conferenceItem.icon },
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = conferenceItem.name,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2
            )

            Text(
                text = conferenceItem.startDate,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
