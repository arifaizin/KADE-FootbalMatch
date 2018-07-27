package id.co.imastudio.kadeproject.player

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.model.PlayersItem
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.content_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val data: List<PlayersItem> = intent.getParcelableArrayListExtra("data")
        val posisi = intent.getIntExtra("posisi", 0)

        detail_player_weight.text = data[posisi].strWeight
        detail_player_height.text = data[posisi].strHeight
        detail_player_position.text = data[posisi].strPosition
        detail_player_overview.text = data[posisi].strDescriptionEN
        supportActionBar?.title = data[posisi].strPlayer
        Picasso.get().load(data[posisi].strThumb).into(detail_player_image)

    }
}
