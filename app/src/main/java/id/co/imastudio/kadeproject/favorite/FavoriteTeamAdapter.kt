package id.co.imastudio.kadeproject.favorite

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.database.FavoriteTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamAdapter(private val teams: List<FavoriteTeam>)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
//        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position])
        holder.itemView.onClick {holder.itemView.context.startActivity<FavoriteTeamDetailActivity>("data" to teams, "posisi" to position )}
    }

    override fun getItemCount(): Int = teams.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(R.id.team_badge)
    private val teamName: TextView = view.find(R.id.team_name)

    fun bindItem(teams: FavoriteTeam) {
        Picasso.get().load(teams.strTeamBadge).into(teamBadge)
        teamName.text = teams.strTeam
    }
}