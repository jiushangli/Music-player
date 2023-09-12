package com.example.m5.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.m5.R
import com.example.m5.databinding.ActivityFeedbackBinding
import com.example.m5.util.setStatusBarTextColor
import com.example.m5.util.transparentStatusBar

class FeedbackActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolGreen)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar(window)
        setStatusBarTextColor(window, false)
        supportActionBar?.title = "反馈"

/*        underBar(
            this,
            AppCompatResources.getDrawable(this, R.drawable.gradient_near_moon)!!,
            binding.mainRecycler
        )*/

        /*binding.sendFA.setOnClickListener {
            val feedbackMsg = binding.feedbackMsgFA.text.toString() + "\n" +
                    binding.emailFA.text.toString()
            val subject = binding.topicFA.text.toString()
            val userName = "2112562745yg@gmail.com"
            val pass = "1230123@google"
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (feedbackMsg.isNotEmpty() && subject.isNotEmpty()
                && (cm.activeNetworkInfo?.isConnectedOrConnecting == true)
            ) {
                thread {
                    try {
                        val properties = Properties()
                        properties["mail.smtp.auth"] = "true"
                        properties["mail.smtp.starttls.enable"] = "true"
                        properties["mail.smtp.host"] = "smtp.gmail.com"
                        properties["mail.smtp.port"] = "587"
                        val session =
                            Session.getInstance(properties, object : javax.mail.Authenticator() {
                                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                                    return javax.mail.PasswordAuthentication(userName, pass)
                                }
                            })
                        val mail = MimeMessage(session)
                        mail.subject = subject
                        mail.setFrom(userName)
                        mail.setRecipients(
                            MimeMessage.RecipientType.TO,
                            InternetAddress.parse(userName)
                        )
                        Transport.send(mail)

                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }.start()
                Toast.makeText(this, "阿珍,你来真的啊", Toast.LENGTH_SHORT).show()
                finish()
            } else
                Toast.makeText(this, "好像有些问题", Toast.LENGTH_SHORT).show()
        }*/
    }
}