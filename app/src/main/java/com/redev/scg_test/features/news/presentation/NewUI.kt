package com.redev.scg_test.features.news.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.redev.scg_test.core.nav.Nav
import com.redev.scg_test.core.nav.NewDetailScreen
import com.redev.scg_test.core.nav.navigateTo
import com.redev.scg_test.features.news.domain.model.Article
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.runBlocking
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewUI(vm:NewViewModel = hiltViewModel(),navController: NavController = Nav.current){
    val state = vm.newPaging.collectAsLazyPagingItems()

    Scaffold(
        topBar = {AppBar()},
    ) { contentPadding ->
        LazyColumn(
            content = {
            /**
             * search box
             */
            item {
                val search = vm.txtSearch.value
                SearchBox(search, onValueChange = {
                    vm.txtSearch.value = it
                }, onSearch = {
                   runBlocking {
                       vm.onSearch()
                   }
                })
            }

                when(state.loadState.refresh) {
                    LoadState.Loading -> {
                      item {
                          CircularProgressIndicator(
                              modifier = Modifier.width(64.dp),
                              color = MaterialTheme.colorScheme.secondary,
                              trackColor = MaterialTheme.colorScheme.surfaceVariant,
                          )
                      }
                    }
                    is LoadState.Error -> {
                        item {
                            Text(text = "Failed")
                        }
                    }
                    else -> {
                        itemsIndexed(state.itemSnapshotList){ _,it->
                          it?.let {
                              Column(modifier = Modifier
                                  .clickable {
                                      /**
                                       * to detail
                                       */
                                      DataDetail.data = it
                                      navController.navigate(NewDetailScreen)
                                  }
                                  .fillParentMaxWidth()
                                  .padding(8.dp),
                                  horizontalAlignment = Alignment.CenterHorizontally) {
                                  AsyncImage(
                                      modifier = Modifier
                                          .width(150.dp)
                                          .height(150.dp),
                                      model = it.urlToImage,
                                      contentDescription = null,
                                      contentScale = ContentScale.Crop
                                  )

                                  Text(text = it.title ?: "N/A",
                                      color = Color(0xFF019715),
                                      overflow = TextOverflow.Ellipsis,
                                  )

                                  Text(text = it.description  ?: "N/A", modifier = Modifier.padding(4.dp))

                                  val actual = OffsetDateTime.parse(it.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
                                  val formatter = DateTimeFormatter.ofPattern("MMMM dd HH:mm")
                                  val formatDateTime = actual.format(formatter)

                                  Text(text = "Update: $formatDateTime")
                                  Divider()
                              }
                          }
                        }
                    }
                }


        }, modifier = Modifier.padding(contentPadding))
    }
}


@Composable
fun SearchBox( search: String,
              onValueChange: (String) -> Unit,
               onSearch:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)

    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 4.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            value = search,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent, cursorColor = Color(0XFF070E14)
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            placeholder = { Text(text = "Search") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
               onSearch()
            })
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("New", modifier = Modifier, textAlign = TextAlign.Center)
        },
    )
}