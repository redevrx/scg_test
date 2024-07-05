package com.redev.scg_test.features.news.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.redev.scg_test.core.nav.Nav
import com.redev.scg_test.features.news.domain.model.Article
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailUI(vm: NewViewModel = hiltViewModel()) {
    val data = DataDetail.data

    if(data == null){
        Text(text = "Data Not Found")
    }else {
        Content(data)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(data:Article){
    Scaffold(
        topBar = { DetailAppBar()}
    ) {  p ->
        LazyColumn(content = {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(data.urlToImage, contentDescription = null,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop,)

                    Text(text = data.title  ?: "N/A",
                        color = Color(0xFF019715),
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(text = data.description ?: "N/A", modifier = Modifier.padding(4.dp))

                    val actual = OffsetDateTime.parse(data.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
                    val formatter = DateTimeFormatter.ofPattern("MMMM dd HH:mm")
                    val formatDateTime = actual.format(formatter)

                    Text(text = "Update: $formatDateTime")
                }
            }
        }, modifier = Modifier.padding(p))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAppBar(nav:NavController = Nav.current){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = "Detail")
        },
        navigationIcon = {
            Row {
                Icon(Icons.Default.ArrowBack, contentDescription = null,
                    modifier = Modifier.clickable {
                        nav.popBackStack()
                    })
                
                Text(text = "Back")
            }
        },
    )
}