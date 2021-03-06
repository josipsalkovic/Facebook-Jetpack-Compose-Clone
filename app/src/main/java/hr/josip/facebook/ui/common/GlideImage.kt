package hr.josip.facebook.ui.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import hr.josip.facebook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GlideImage(
    model: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onImageReady: (() -> Unit)? = null,
    requestOptions: RequestOptions = RequestOptions(),
    customize: RequestBuilder<Bitmap>.() -> RequestBuilder<Bitmap> = { this },
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    val context = LocalContext.current

    val glide = Glide.with(context)
    var target: CustomTarget<Bitmap>?
    CoroutineScope(Dispatchers.Main).launch {
        target = object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                image = null
                drawable = placeholder
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?,
            ) {
                image = resource.asImageBitmap()
                onImageReady?.invoke()
            }
        }

        glide
            .asBitmap()
            .load(model)
            .let(customize)
            .apply(requestOptions)
            .into(target!!)
    }

    ActiveImage(
        image = image,
        drawable = drawable,
        modifier = modifier,
        contentScale = contentScale,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

@Composable
private fun ActiveImage(
    image: ImageBitmap?,
    drawable: Drawable?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    when {
        image != null -> Crossfade(Unit) {
            Image(
                bitmap = image,
                contentDescription = stringResource(id = R.string.app_name),
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
        drawable != null -> Crossfade(Unit) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier)
            ) {
                drawIntoCanvas { drawable.draw(it.nativeCanvas) }
            }
        }
        else -> Crossfade(Unit) {
            Box(modifier = modifier.background(Color.Transparent))
        }
    }
}