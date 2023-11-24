package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smallrecord.databinding.VaccineBinding

class Vaccine : AppCompatActivity() {

    private var _binding: VaccineBinding? = null
    private val binding get() = _binding!!
    private val vaccinelist = ArrayList<vaccine_section>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = VaccineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val babyback = findViewById<ImageButton>(R.id.babyback)
        babyback.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        // RecyclerView 설정
        setupRecyclerView()
        // 데이터 로드
        loadData()
    }

    private fun setupRecyclerView() {
        binding.vaccinelist.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@Vaccine)
            adapter = vaccine_adapter(vaccinelist)
        }
    }

    private fun loadData(){
        val category1 = "0개월"
        val cate1vaccine = ArrayList<vaccine_item>()
        cate1vaccine.add(vaccine_item("BCG", "대상병명: 결핵", "차수: 1회", "접종일: "))
        cate1vaccine.add(vaccine_item("HepB", "대상병명: B형 간염", "차수: 1차", "접종일: "))

        val category2 = "1개월"
        val cate2vaccine = ArrayList<vaccine_item>()
        cate2vaccine.add(vaccine_item("HepB", "대상병명: B형 간염", "차수: 2차", "접종일: "))

        val category3 = "2개월"
        val cate3vaccine = ArrayList<vaccine_item>()
        cate3vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 1차", "접종일: "))
        cate3vaccine.add(vaccine_item("IPV", "대상병명: 폴리오", "차수: 1차", "접종일: "))
        cate3vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 1차", "접종일: "))
        cate3vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 1차", "접종일: "))
        cate3vaccine.add(vaccine_item("RV(로타릭스)", "대상병명: 로타바이러스", "차수: 1차", "접종일: "))
        cate3vaccine.add(vaccine_item("RV(로타텍)", "대상병명: 로타바이러스", "차수: 1차", "접종일: "))

        val category4 = "4개월"
        val cate4vaccine = ArrayList<vaccine_item>()
        cate4vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 2차", "접종일: "))
        cate4vaccine.add(vaccine_item("IPV", "대상병명: 폴리오", "차수: 2차", "접종일: "))
        cate4vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 2차", "접종일: "))
        cate4vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 2차", "접종일: "))
        cate4vaccine.add(vaccine_item("RV(로타릭스)", "대상병명: 로타바이러스", "차수: 2차", "접종일: "))
        cate4vaccine.add(vaccine_item("RV(로타텍)", "대상병명: 로타바이러스", "차수: 2차", "접종일: "))

        val category5 = "6개월"
        val cate5vaccine = ArrayList<vaccine_item>()
        cate5vaccine.add(vaccine_item("HepB", "대상병명: B형 간염", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("IPV", "대상병명: 폴리오", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("RV(로타텍)", "대상병명: 로타바이러스", "차수: 3차", "접종일: "))
        cate5vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category6 = "12개월"
        val cate6vaccine = ArrayList<vaccine_item>()
        cate6vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("MMR", "대상병명: 홍역, 유행성 이하선염, 풍진", "차수: 1차(12개월~15개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("VAR", "대상병명: 수두", "차수: 1회(12개월~15개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 1차(12개월~18개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("IJEV(불활성화 백신)", "대상병명: 일본뇌염", "차수: 1차", "접종일: "))
        cate6vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 1차(12개월~24개월 내)", "접종일: "))
        cate6vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category7 = "13개월"
        val cate7vaccine = ArrayList<vaccine_item>()
        cate7vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("MMR", "대상병명: 홍역, 유행성 이하선염, 풍진", "차수: 1차(12개월~15개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("VAR", "대상병명: 수두", "차수: 1회(12개월~15개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 1차(12개월~18개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("IJEV(불활성화 백신)", "대상병명: 일본뇌염", "차수: 2차", "접종일: "))
        cate7vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 1차(12개월~24개월 내)", "접종일: "))
        cate7vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category8 = "15개월"
        val cate8vaccine = ArrayList<vaccine_item>()
        cate8vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 4차(15개월~18개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("Hib", "대상병명: B형 헤모필루스 인플루엔자", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("PCV", "대상병명: 폐렴구균", "차수: 4차(12개월~15개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("MMR", "대상병명: 홍역, 유행성 이하선염, 풍진", "차수: 1차(12개월~15개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("VAR", "대상병명: 수두", "차수: 1회(12개월~15개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 1차(12개월~18개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 1차(12개월~24개월 내)", "접종일: "))
        cate8vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category9 = "18개월"
        val cate9vaccine = ArrayList<vaccine_item>()
        cate9vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 4차(15개월~18개월 내)", "접종일: "))
        cate9vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 1차(12개월~18개월 내)", "접종일: "))
        cate9vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 1차(12개월~24개월 내)", "접종일: "))
        cate9vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category10 = "24개월"
        val cate10vaccine = ArrayList<vaccine_item>()
        cate10vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))
        cate10vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 2차(1차 접종 6개월 후, 24개월~36개월 내)", "접종일: "))
        cate10vaccine.add(vaccine_item("IJEV(불활성화 백신)", "대상병명: 일본뇌염", "차수: 3차", "접종일: "))
        cate10vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 1차(12개월~24개월 내)", "접종일: "))
        cate10vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category11 = "36개월"
        val cate11vaccine = ArrayList<vaccine_item>()
        cate11vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))
        cate11vaccine.add(vaccine_item("HepA", "대상병명: A형 간염", "차수: 2차(1차 접종 6개월 후, 24개월~36개월 내)", "접종일: "))
        cate11vaccine.add(vaccine_item("LJEV(햄스터 신장세포 유래 약독학 생백신)", "대상병명: 일본뇌염", "차수: 2차(1차 접종 12개월 후)", "접종일: "))
        cate11vaccine.add(vaccine_item("IIV", "대상병명: 인플루엔자", "차수: 우선접종권장 대상자", "접종일: "))

        val category12 = "만 4세"
        val cate12vaccine = ArrayList<vaccine_item>()
        cate12vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 5차(만 4세~만 6세 내)", "접종일: "))
        cate12vaccine.add(vaccine_item("IPV", "대상병명: 폴리오", "차수: 4차(만 4세~만 6세 내)", "접종일: "))
        cate12vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))
        cate12vaccine.add(vaccine_item("MMR", "대상병명: 홍역, 유행성 이하선염, 풍진", "차수: 2차(만 4세~만 6세 내)", "접종일: "))

        val category13 = "만 6세"
        val cate13vaccine = ArrayList<vaccine_item>()
        cate13vaccine.add(vaccine_item("DTap", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 5차(만 4세~만 6세 내)", "접종일: "))
        cate13vaccine.add(vaccine_item("IPV", "대상병명: 폴리오", "차수: 4차(만 4세~만 6세 내)", "접종일: "))
        cate13vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))
        cate13vaccine.add(vaccine_item("MMR", "대상병명: 홍역, 유행성 이하선염, 풍진", "차수: 2차(만 4세~만 6세 내)", "접종일: "))
        cate13vaccine.add(vaccine_item("IJEV(불활성화 백신)", "대상병명: 일본뇌염", "차수: 4차", "접종일: "))

        val category14 = "만 11세"
        val cate14vaccine = ArrayList<vaccine_item>()
        cate14vaccine.add(vaccine_item("TdaP", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 6차(만 11세~만 12세 내)", "접종일: "))
        cate14vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))

        val category15 = "만 11세"
        val cate15vaccine = ArrayList<vaccine_item>()
        cate15vaccine.add(vaccine_item("TdaP", "대상병명: 디프테리아, 파상풍, 백일해", "차수: 6차(만 11세~만 12세 내)", "접종일: "))
        cate15vaccine.add(vaccine_item("PPSV", "대상병명: 폐렴구균", "차수: 고위험군 한정 대상자", "접종일: "))
        cate15vaccine.add(vaccine_item("IJEV(불활성화 백신)", "대상병명: 일본뇌염", "차수: 5차", "접종일: "))
        cate15vaccine.add(vaccine_item("HPV", "대상병명: 사람유두종 바이러스 감염증", "차수: 1~2차", "접종일: "))

        vaccinelist.add(vaccine_section(category1, cate1vaccine))
        vaccinelist.add(vaccine_section(category2, cate2vaccine))
        vaccinelist.add(vaccine_section(category3, cate3vaccine))
        vaccinelist.add(vaccine_section(category4, cate4vaccine))
        vaccinelist.add(vaccine_section(category5, cate5vaccine))
        vaccinelist.add(vaccine_section(category6, cate6vaccine))
        vaccinelist.add(vaccine_section(category7, cate7vaccine))
        vaccinelist.add(vaccine_section(category8, cate8vaccine))
        vaccinelist.add(vaccine_section(category9, cate9vaccine))
        vaccinelist.add(vaccine_section(category10, cate10vaccine))
        vaccinelist.add(vaccine_section(category11, cate11vaccine))
        vaccinelist.add(vaccine_section(category12, cate12vaccine))
        vaccinelist.add(vaccine_section(category13, cate13vaccine))
        vaccinelist.add(vaccine_section(category14, cate14vaccine))
        vaccinelist.add(vaccine_section(category15, cate15vaccine))
    }
}