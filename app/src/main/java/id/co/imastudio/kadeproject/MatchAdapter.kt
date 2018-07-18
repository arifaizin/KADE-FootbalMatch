package id.co.imastudio.kadeproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.co.imastudio.kadeproject.model.EventsItem
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MatchAdapter(private val events: List<EventsItem>)
    : RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
//        return MatchViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
        return MatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(events[position])
        holder.itemView.onClick {holder.itemView.context.startActivity<MatchDetailActivity>("data" to events, "posisi" to position )}
    }

    override fun getItemCount(): Int = events.size

}

//class MatchUI : AnkoComponent<ViewGroup> {
//    override fun createView(ui: AnkoContext<ViewGroup>): View {
//        return with(ui) {
//            linearLayout {
//                lparams(width = matchParent, height = wrapContent)
//                padding = dip(16)
//                orientation = LinearLayout.VERTICAL
//
//                textView {
//                    id = R.id.match_time
//                    textSize = 16f
//                }.lparams{
//                    margin = dip(15)
//                    width = matchParent
//                    gravity = Gravity.CENTER_HORIZONTAL
//                }
//
//                verticalLayout {
//                    lparams(width = matchParent, height = wrapContent)
//                    padding = dip(16)
//                    orientation = LinearLayout.HORIZONTAL
//                    gravity = Gravity.CENTER
//                    weightSum = 1f
//
//                    textView {
//                        id = R.id.match_home_team
//                        textSize = 16f
//                    }.lparams{
//                        margin = dip(5)
//                    }
//
//                    textView {
//                        id = R.id.match_home_score
//                        textSize = 16f
//                    }.lparams{
//                        margin = dip(5)
//                    }
//
//                    textView ("vs"){
//                        textSize = 16f
//                    }.lparams{
//                        margin = dip(5)
//                    }
//
//                    textView {
//                        id = R.id.match_away_score
//                        textSize = 16f
//                    }.lparams{
//                        margin = dip(5)
//                    }
//
//                    textView {
//                        id = R.id.match_away_team
//                        textSize = 16f
//                    }.lparams{
//                        margin = dip(5)
//                    }
//
//
//                }
//
//            }
//        }
//    }
//
//}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view){

    //todo 3 item
//    private val teamBadge: ImageView = view.find(R.id.team_badge)
//    private val teamName: TextView = view.find(R.id.team_name)
    private val matchTime: TextView = view.find(R.id.match_time)
    private val matchHomeTeam: TextView = view.find(R.id.match_home_team)
    private val matchHomeScore: TextView = view.find(R.id.match_home_score)
    private val matchAwayScore: TextView = view.find(R.id.match_away_score)
    private val matchAwayTeam: TextView = view.find(R.id.match_away_team)

    fun bindItem(events: EventsItem) {
//        Picasso.get().load(events.teamBadge).into(teamBadge)
//        teamName.text = events.teamName
        matchTime.text = events.dateEvent
        matchHomeTeam.text = events.strHomeTeam
        matchAwayTeam.text = events.strAwayTeam
        matchHomeScore.text = events.intHomeScore
        matchAwayScore.text = events.intAwayScore


    }
}