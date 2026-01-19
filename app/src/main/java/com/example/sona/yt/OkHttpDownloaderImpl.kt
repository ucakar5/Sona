
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import okhttp3.Request as OkRequest

class OkHttpDownloaderImpl : Downloader() {
    private val client = OkHttpClient()

    override fun execute(request: Request): Response {
        // Use OkHttp request
        val urlField = Request::class.java.getDeclaredField("url")
        urlField.isAccessible = true
        val urlString = urlField.get(request) as String

        val okReq = OkRequest.Builder().url(urlString).build()
        val okResp = client.newCall(okReq).execute()

        val bodyString = okResp.body?.string() ?: ""
        val headers = okResp.headers.toMultimap()
        val latestUrl = okResp.request.url.toString()
        val code = okResp.code
        val message = okResp.message

        // Call Java constructor with positional arguments (no named arguments!)
        return Response(code, message, headers, bodyString, latestUrl)
    }
}


