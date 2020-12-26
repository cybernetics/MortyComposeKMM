package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.paging.PagingSource
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

class LocationsDataSource(private val repository: MortyRepository) : PagingSource<Int, LocationDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDetail> {
        val pageNumber = params.key ?: 0

        val locationsResponse = repository.getLocations(pageNumber)
        val episodes = locationsResponse?.resultsFilterNotNull()?.map { it.fragments.locationDetail }

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = locationsResponse?.info?.next
        return LoadResult.Page(data = episodes ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}