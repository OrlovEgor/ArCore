package ru.orlovegor.arcore

import android.opengl.GLSurfaceView
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Session
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class ArCoreRenderer(val activity: MainActivity, val session: Session): GLSurfaceView.Renderer {


    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onDrawFrame(p0: GL10?) {
        if (session == null){
            return
        }
        val frame = session.update() //обновляем сессию
        val camera = frame.camera // создаем камеру или хз че
        val lightIntensity = frame.lightEstimate.pixelIntensity // освещение
        val hitResults = frame.hitTest(0F, 0F)
        var hit : HitResult?
        if(hitResults.size >0){
            hit = getClosestHit(hitResults)
            val anchor = hit?.createAnchor()
        }


    }
    private fun getClosestHit(hitResults: List<HitResult>): HitResult? {
        for (hitResult in hitResults) {
            if (hitResult.trackable is Plane) {
                return hitResult
            }
        }
        return hitResults[0]
    }

}