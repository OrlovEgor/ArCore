package ru.orlovegor.arcore

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.collision.Plane
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.utils.getResourceUri
import ru.orlovegor.arcore.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    var placementIsDone = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "START APP")


        //создание модели
        val modelNode = ArModelNode(
           placementMode = PlacementMode.PLANE_HORIZONTAL,
            hitPosition = Position(0.0f,0.0f,-2.0f),
            followHitPosition = true,
            instantAnchor = false
        )

        //загрузка модели

        lifecycleScope.launchWhenCreated {
            val modelInstance = modelNode.loadModelGlb(
                context = this@MainActivity,
                glbFileLocation = this@MainActivity.getResourceUri(R.raw.new_zemlya),
                lifecycle = lifecycle,
                scaleToUnits= null,
                autoAnimate = false,
                centerOrigin = Position(x = 0.0f, y = 0.0f, z = 0.0f),
                onError = { Log.d("TAG", "scene error error = ${it.toString()}") }
            )

        }
        val frame = binding.arSceneView.currentFrame
        val point = getScreenCenter()
        if (frame!=null){
            val hits = frame.hitTests(point.x.toFloat(), point.y.toFloat())
            for (hit in hits){

            val trackable = hit.trackable
                if (trackable is Plane ){

                }
            }
        }
      //  binding.arSceneView.addChild(modelNode)  //добавляем в сцену модель

    }
    private fun getScreenCenter(): android.graphics.Point {
        val vw = findViewById<View>(android.R.id.content)
        return Point(vw.width / 2, vw.height / 2)
    }


    private fun createSession() {
        val session = Session(this)
        val config = Config(session)
        //config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
        session.configure(config)
    }


    private fun cameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            Log.d("TAG", "Camera permission")
            Log.d("TAG", "PERM DENIED")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        } else {
            Log.d("TAG", "PERM GRANTED")
        }
    }

    }
