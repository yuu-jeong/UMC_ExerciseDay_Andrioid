package com.example.exerciseday_android.ui.join

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.exerciseday_android.EmailCheckView
import com.example.exerciseday_android.JoinService
import com.example.exerciseday_android.R
import com.example.exerciseday_android.databinding.ActivityJoinInfoBinding
import java.util.regex.Pattern


class JoinInfoActivity : AppCompatActivity(), EmailCheckView, View.OnClickListener {

    lateinit var binding: ActivityJoinInfoBinding

    var emailPattern: Pattern = Patterns.EMAIL_ADDRESS
    var focus = false
    var isEmailValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO
        // 뒤로가기 버튼
        // 이메일 중복 검사
        // 버튼 색 변경


        // 뒤로 가기
        binding.joinBackBtn.setOnClickListener(this)

        // 다음 버튼 클릭 시
        binding.joinNextBtn.setOnClickListener(this)

        // 이메일 중복 버튼 클릭 시
        binding.joinEmailCheckBtn.setOnClickListener {
            emailCheck()
        }

        checkEmail()
        checkNickname()
        checkPassword()
        checkPasswordCheck()

        // 모든 조건 충족 시
//        if (checkNickname()) {
//            Log.d("Button", "true")
//            // 다음 버튼 색 변경
//            binding.joinNextBtn.setBackgroundResource(R.drawable.join_valid_btn_shape)
//            binding.joinNextBtn.setTextColor(Color.parseColor("#ffffff"))
//        }

//        if (binding.joinEmailErrorTv.visibility == View.GONE && binding.joinNicknameErrorTv.visibility == View.GONE
//            && binding.joinPasswordErrorTv.visibility == View.GONE && binding.joinPasswordCheckErrorTv.visibility == View.GONE
//        ) {
//            // 다음 버튼 색 변경
//        }

    }

    private fun emailCheck() {
        val email: String = binding.joinEmailEt.text.toString()

        val joinService = JoinService()
        joinService.setEmailCheckView(this)

        joinService.emailCheck(email)
    }

    override fun onEmailCheckSuccess() {
        Log.d("EMAIL_CHECK/SUCCESS", "사용 가능한 이메일입니다.")

        isEmailValid = true

        binding.joinEmailErrorTv.text = "사용 가능한 이메일입니다."
        binding.joinEmailErrorTv.visibility = View.VISIBLE

        binding.joinEmailErrorTv.setTextColor(Color.parseColor("#000000"))
    }

    override fun onEmailCheckFailure(message: String) {
        Log.d("IDCHECK/FAILURE", "이미 회원가입된 이메일입니다.")

        isEmailValid = false

        binding.joinEmailErrorTv.text = message    // "이미 회원가입된 이메일입니다."
        binding.joinEmailErrorTv.visibility = View.VISIBLE

//        binding.joinEmailErrorTv.setTextColor(Color.parseColor("#ED022D"))
    }


    private fun allCheck(): Boolean {

        // 이메일 체크
        if (binding.joinEmailEt.text.isEmpty()) {
            binding.joinEmailErrorTv.text = "올바른 이메일을 입력해주세요."
            binding.joinEmailErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
        } else if (!emailPattern.matcher(binding.joinEmailEt.text)
                .matches()
        ) {
            binding.joinEmailErrorTv.text = "이메일 형식을 확인해주세요."
            binding.joinEmailErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)

        } else {
            if (!isEmailValid) {
                binding.joinEmailErrorTv.text = "이메일 중복 확인을 해주세요."
                binding.joinEmailErrorTv.visibility = View.VISIBLE
                // EditText border 색 변경
                binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
            } else {
                binding.joinEmailErrorTv.visibility = View.GONE
                // EditText border 색 변경 (원래대로)
                binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_shape)
            }

            binding.joinEmailErrorTv.visibility = View.GONE  // 이메일 중복 해결 시 삭제
        }


        // 닉네임 체크
        if (binding.joinNicknameEt.text.isEmpty()) {
            binding.joinNicknameErrorTv.text = "닉네임을 입력해주세요."
            binding.joinNicknameErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinNicknameEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
        } else {
            binding.joinNicknameErrorTv.visibility = View.GONE
            // EditText border 색 변경 (원래대로)
            binding.joinNicknameEt.setBackgroundResource(R.drawable.join_edittext_shape)
        }

        // 비밀번호 체크
        if (binding.joinPasswordEt.text.isEmpty() || binding.joinPasswordEt.text.length < 8 || binding.joinPasswordEt.text.length > 16) {
            binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
            binding.joinPasswordErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
        } else if (!Pattern.matches(
                "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$",
                binding.joinPasswordEt.text.toString()
            )
        ) {
            binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
            binding.joinPasswordErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
        } else {
            binding.joinPasswordErrorTv.visibility = View.GONE
            // EditText border 색 변경 (원래대로)
            binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_shape)
        }

        // 비밀번호 확인 체크
        if (binding.joinPasswordEt.text.toString() != binding.joinPasswordCheckEt.text.toString()) {
            binding.joinPasswordCheckErrorTv.text = "입력하신 비밀번호와 일치하지 않아요."
            binding.joinPasswordCheckErrorTv.visibility = View.VISIBLE
            // EditText border 색 변경
            binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
        } else {
            binding.joinPasswordCheckErrorTv.visibility = View.GONE
            // EditText border 색 변경 (원래대로)
            binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_shape)
        }

        // 모든 조건 충족 시
        if (binding.joinEmailErrorTv.visibility == View.GONE && binding.joinNicknameErrorTv.visibility == View.GONE
            && binding.joinPasswordErrorTv.visibility == View.GONE && binding.joinPasswordCheckErrorTv.visibility == View.GONE
        ) {
            val email: String = binding.joinEmailEt.text.toString()
            val nickname: String = binding.joinNicknameEt.text.toString()
            val password: String = binding.joinPasswordEt.text.toString()

            // JoinPhoneActivity 로 가입 입력정보 전달
            val joinInfoList = ArrayList<String>()
            joinInfoList.add(email)
            joinInfoList.add(nickname)
            joinInfoList.add(password)

            val intent = Intent(this, JoinPhoneActivity::class.java)
            intent.putStringArrayListExtra("joinInfo", joinInfoList)

            startActivity(intent)

            return true
        } else return false
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.join_back_btn -> {
                // 뒤로 가기
//                var intent = Intent(this, LoginActivity.class)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.join_next_btn -> allCheck()
        }
    }


    private fun checkEmail(): Boolean {
        binding.joinEmailEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                focus = true  // 한 번이라도 포커스를 가졌으면 true

                if (binding.joinEmailEt.text.isNotEmpty()) {
                    binding.joinEmailRemoveBtn.visibility = View.VISIBLE

                    binding.joinEmailRemoveBtn.setOnClickListener {
                        binding.joinEmailEt.text.clear()
                    }
                } else {
                    binding.joinEmailRemoveBtn.visibility = View.INVISIBLE
                }

                binding.joinEmailEt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.isNotEmpty() == true) {
                            binding.joinEmailRemoveBtn.visibility = View.VISIBLE

                            binding.joinEmailRemoveBtn.setOnClickListener {
                                binding.joinEmailEt.text.clear()
                            }
                        } else {
                            binding.joinEmailRemoveBtn.visibility = View.INVISIBLE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            } else {
                if (focus) {
                    if (binding.joinEmailEt.text.isEmpty()) {
                        binding.joinEmailErrorTv.text = "올바른 이메일을 입력해주세요."
                        binding.joinEmailErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else if (!emailPattern.matcher(binding.joinEmailEt.text)
                            .matches()
                    ) {
                        binding.joinEmailErrorTv.text = "이메일 형식을 확인해주세요."
                        binding.joinEmailErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)

                    } else {
                        binding.joinEmailErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_shape)
//                        if (!isEmailValid) {
//                            binding.joinEmailErrorTv.text = "이메일 중복 확인을 해주세요."
//                            binding.joinEmailErrorTv.visibility = View.VISIBLE
//                            // EditText border 색 변경
//                            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
//                        } else {
//                            binding.joinEmailErrorTv.visibility = View.GONE
//                            // EditText border 색 변경 (원래대로)
//                            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_shape)
//                        }
                    }
                } else {

//                    if (binding.joinEmailEt.text.isEmpty()) {
//                        binding.joinEmailErrorTv.text = "올바른 이메일을 입력해주세요."
//                        binding.joinEmailErrorTv.visibility = View.VISIBLE
//                        // EditText border 색 변경
//                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
//                    } else if (!emailPattern.matcher(binding.joinEmailEt.text)
//                            .matches()
//                    ) {
//                        binding.joinEmailErrorTv.text = "이메일 형식을 확인해주세요."
//                        binding.joinEmailErrorTv.visibility = View.VISIBLE
//                        // EditText border 색 변경
//                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
//
//                    } else {
//                        binding.joinEmailErrorTv.visibility = View.GONE
//                        // EditText border 색 변경 (원래대로)
//                        binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_shape)
//
//                        if (!isEmailValid) {
//                            binding.joinEmailErrorTv.text = "이메일 중복 확인을 해주세요."
//                            binding.joinEmailErrorTv.visibility = View.VISIBLE
//                            // EditText border 색 변경
//                            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
//                        } else {
//                            binding.joinEmailErrorTv.visibility = View.GONE
//                            // EditText border 색 변경 (원래대로)
//                            binding.joinEmailEt.setBackgroundResource(R.drawable.join_edittext_shape)
//                        }
//                    }
                }

                binding.joinEmailRemoveBtn.visibility = View.INVISIBLE
            }
        }

        return binding.joinEmailEt.text.isNotEmpty() && (binding.joinEmailErrorTv.visibility == View.GONE)
    }

    private fun checkNickname(): Boolean {
        binding.joinNicknameEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                focus = true  // 한 번이라도 포커스를 가졌으면 true

                if (binding.joinNicknameEt.text.isNotEmpty()) {
                    binding.joinNicknameRemoveBtn.visibility = View.VISIBLE

                    binding.joinNicknameRemoveBtn.setOnClickListener {
                        binding.joinNicknameEt.text.clear()
                    }
                } else {
                    binding.joinNicknameRemoveBtn.visibility = View.INVISIBLE
                }

                binding.joinNicknameEt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.isNotEmpty() == true) {
                            binding.joinNicknameRemoveBtn.visibility = View.VISIBLE

                            binding.joinNicknameRemoveBtn.setOnClickListener {
                                binding.joinNicknameEt.text.clear()
                            }
                        } else {
                            binding.joinNicknameRemoveBtn.visibility = View.INVISIBLE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            } else {
                if (focus) {
                    if (binding.joinNicknameEt.text.isEmpty()) {
                        binding.joinNicknameErrorTv.text = "닉네임을 입력해주세요."
                        binding.joinNicknameErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinNicknameEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else {
                        binding.joinNicknameErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinNicknameEt.setBackgroundResource(R.drawable.join_edittext_shape)
                    }
                }

                binding.joinNicknameRemoveBtn.visibility = View.INVISIBLE
            }
        }

//        Log.d("nickname", binding.joinNicknameEt.text.toString())
        return binding.joinNicknameErrorTv.visibility == View.GONE
    }


    private fun checkPassword(): Boolean {
        binding.joinPasswordEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                focus = true  // 한 번이라도 포커스를 가졌으면 true

                if (binding.joinPasswordEt.text.isNotEmpty()) {
                    binding.joinPasswordRemoveBtn.visibility = View.VISIBLE

                    binding.joinPasswordRemoveBtn.setOnClickListener {
                        binding.joinPasswordEt.text.clear()
                    }
                } else {
                    binding.joinPasswordRemoveBtn.visibility = View.INVISIBLE
                }

                binding.joinPasswordEt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.isNotEmpty() == true) {
                            binding.joinPasswordRemoveBtn.visibility = View.VISIBLE

                            binding.joinPasswordRemoveBtn.setOnClickListener {
                                binding.joinPasswordEt.text.clear()
                            }
                        } else {
                            binding.joinPasswordRemoveBtn.visibility = View.INVISIBLE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            } else {
                if (focus) {
                    if (binding.joinPasswordEt.text.isEmpty() || binding.joinPasswordEt.text.length < 8 || binding.joinPasswordEt.text.length > 16) {
                        binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
                        binding.joinPasswordErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else if (!Pattern.matches(
                            "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$",
                            binding.joinPasswordEt.text.toString()
                        )
                    ) {
                        binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
                        binding.joinPasswordErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else {
                        binding.joinPasswordErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_shape)
                    }
                } else {
                    if (binding.joinPasswordEt.text.isEmpty() || binding.joinPasswordEt.text.length < 8 || binding.joinPasswordEt.text.length > 16) {
                        binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
                        binding.joinPasswordErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else if (!Pattern.matches(
                            "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$",
                            binding.joinPasswordEt.text.toString()
                        )
                    ) {
                        binding.joinPasswordErrorTv.text = "숫자/영문/특수문자를 포함해 8~16자로 입력해주세요."
                        binding.joinPasswordErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else {
                        binding.joinPasswordErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinPasswordEt.setBackgroundResource(R.drawable.join_edittext_shape)
                    }
                }

                binding.joinPasswordRemoveBtn.visibility = View.INVISIBLE
            }
        }

        return binding.joinPasswordErrorTv.visibility == View.GONE
    }

    private fun checkPasswordCheck(): Boolean {
        binding.joinPasswordCheckEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                focus = true  // 한 번이라도 포커스를 가졌으면 true

                if (binding.joinPasswordCheckEt.text.isNotEmpty()) {
                    binding.joinPasswordCheckRemoveBtn.visibility = View.VISIBLE

                    binding.joinPasswordCheckRemoveBtn.setOnClickListener {
                        binding.joinPasswordCheckEt.text.clear()
                    }
                } else {
                    binding.joinPasswordCheckRemoveBtn.visibility = View.INVISIBLE
                }

                binding.joinPasswordCheckEt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.isNotEmpty() == true) {
                            binding.joinPasswordCheckRemoveBtn.visibility = View.VISIBLE

                            binding.joinPasswordCheckRemoveBtn.setOnClickListener {
                                binding.joinPasswordCheckEt.text.clear()
                            }
                        } else {
                            binding.joinPasswordCheckRemoveBtn.visibility = View.INVISIBLE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })

            } else {
                if (focus) {
                    if (binding.joinPasswordEt.text.toString() != binding.joinPasswordCheckEt.text.toString()) {
                        binding.joinPasswordCheckErrorTv.text = "입력하신 비밀번호와 일치하지 않아요."
                        binding.joinPasswordCheckErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else {
                        binding.joinPasswordCheckErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_shape)
                    }
                } else {
                    if (binding.joinPasswordEt.text.toString() != binding.joinPasswordCheckEt.text.toString()) {
                        binding.joinPasswordCheckErrorTv.text = "입력하신 비밀번호와 일치하지 않아요."
                        binding.joinPasswordCheckErrorTv.visibility = View.VISIBLE
                        // EditText border 색 변경
                        binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_error_shape)
                    } else {
                        binding.joinPasswordCheckErrorTv.visibility = View.GONE
                        // EditText border 색 변경 (원래대로)
                        binding.joinPasswordCheckEt.setBackgroundResource(R.drawable.join_edittext_shape)
                    }
                }

                binding.joinPasswordCheckRemoveBtn.visibility = View.INVISIBLE
            }
        }

        return binding.joinPasswordCheckErrorTv.visibility == View.GONE
    }

}