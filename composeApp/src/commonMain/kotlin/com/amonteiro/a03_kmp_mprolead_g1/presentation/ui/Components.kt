package com.amonteiro.a03_kmp_mprolead_g1.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PictureGallery(modifier: Modifier = Modifier, urlList: List<String>)