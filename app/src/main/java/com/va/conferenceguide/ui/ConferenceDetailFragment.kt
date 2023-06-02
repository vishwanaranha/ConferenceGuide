package com.va.conferenceguide.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.va.conferenceguide.R
import com.va.conferenceguide.data.models.Data

class ConferenceDetailFragment : Fragment() {

    private val args: ConferenceDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    ConferenceDetailLayout(
                        conferenceData = args.data,
                        navController = findNavController()
                    )
                }
            }
        }
    }
}

@Composable
private fun ConferenceDetailLayout(conferenceData: Data, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = conferenceData.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigate Up",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .offset(x = 8.dp)
                            .clip(CircleShape)
                            .size(36.dp)
                            .clickable { navController.popBackStack() }
                            .padding(6.dp)
                    )

                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
        ) {

            GlideImage(
                imageModel = { conferenceData.icon },
                modifier = Modifier.fillMaxWidth(),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillWidth
                )
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = conferenceData.name,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                DetailItem(
                    label = stringResource(id = R.string.start_date),
                    value = conferenceData.startDate
                )

                DetailItem(
                    label = stringResource(id = R.string.end_date),
                    value = conferenceData.endDate
                )

            }

        }
    }

}

@Composable
fun DetailItem(
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = label.plus(":"),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = value,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
    }
}