package ru.chieffly.memoscope

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentDash : Fragment() {
    val client by lazy {
        NetworkService.create()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash, container, false)
        val textFragment = view?.findViewById(R.id.textViewR) as TextView
        val progressBar = view?.findViewById(R.id.DashProgressBar) as ProgressBar
        textFragment.visibility= TextView.INVISIBLE

        val prefs = Prefs(getActivity()!!.getApplicationContext())
        val token = prefs.getString("Token")
        println (token)
        client.getMemes().enqueue(object : Callback<List<MemDto>> {
            override fun onFailure(call: Call<List<MemDto>>?, t: Throwable?) {
                progressBar.visibility= TextView.INVISIBLE
                textFragment.visibility= TextView.VISIBLE
                textFragment.text = getString(R.string.err_dash)
            }
            override fun onResponse(call: Call<List<MemDto>>?, response: Response<List<MemDto>>?) {
                if (response?.isSuccessful()!!) {

                    val list = response.body() //список мемов
                    progressBar.visibility= TextView.INVISIBLE

                } else {
                    progressBar.visibility= TextView.INVISIBLE
                    textFragment.visibility= TextView.VISIBLE
                    textFragment.text = getString(R.string.err_dash)
                }
            }
        })
        return view;

    }
}