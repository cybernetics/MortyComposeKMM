package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import org.koin.androidx.compose.getViewModel


@Composable
fun LocationDetailView(locationId: String, popBack: () -> Unit) {
    val locationsListViewModel = getViewModel<LocationsListViewModel>()
    val (location, setLocation) = remember { mutableStateOf<LocationDetail?>(null) }

    LaunchedEffect(locationId) {
        setLocation(locationsListViewModel.getLocation(locationId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(location?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        })
    {
        Surface(color = Color.LightGray) {

            ScrollableColumn(modifier = Modifier.padding(top = 16.dp)) {
                location?.let {

                    Text("Residents", style = MaterialTheme.typography.h5, color = AmbientContentColor.current,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                    Surface(color = Color.White) {
                        LocationResidentList(location)
                    }

                }
            }
        }
    }

}

@Composable
private fun LocationResidentList(location: LocationDetail) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        location.residents?.let { residentList ->
            residentList.filterNotNull().forEach { resident ->
                Row(modifier = Modifier.padding(vertical = 8.dp)) {

                    Surface(
                        modifier = Modifier.preferredSize(28.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    ) {
                        resident.image?.let {
                            CoilImage(
                                data = it,
                                modifier = Modifier.preferredSize(28.dp),
                                requestBuilder = {
                                    transformations(CircleCropTransformation())
                                }
                            )
                        }
                    }

                    Text(resident.name ?: "",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        style = MaterialTheme.typography.h6)
                }
                Divider()
            }
        }
    }
}

