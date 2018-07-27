package id.co.imastudio.kadeproject.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.co.imastudio.kadeproject.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx


class OverviewFragment : Fragment(), AnkoComponent<Context> {

    private var parameter: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        parameter = getArguments()?.getString("parameter")


//        val teamOverview = view?.find(R.id.team_overview)
//                team_overview.text ?:  = parameter

        return TextView(activity).apply {
            setText(parameter)
        }
//        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)


            textView {
                id = R.id.team_overview
                textSize = 16f
            }.lparams{
                margin = dip(15)
            }


        }

    }





}
