<template>
  <div style="background-color: #ffe6f0; min-height: 100vh; padding: 20px;">
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card shadow="hover" :body-style="{ padding: '0px' }">
        <div class="card-content">
          <div class="card-left">
            <el-icon><user /></el-icon>
          </div>
          <div class="card-right">
            <div class="card-num">{{ userCount }}</div>
            <div>用户总数</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" :body-style="{ padding: '0px' }">
        <div class="card-content">
          <div class="card-left">
            <el-icon><headset /></el-icon>
          </div>
          <div class="card-right">
            <div class="card-num">{{ songCount }}</div>
            <div>歌曲总数</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" :body-style="{ padding: '0px' }">
        <div class="card-content">
          <div class="card-left">
            <el-icon><mic /></el-icon>
          </div>
          <div class="card-right">
            <div class="card-num">{{ singerCount }}</div>
            <div>歌手数量</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover" :body-style="{ padding: '0px' }">
        <div class="card-content">
          <div class="card-left">
            <el-icon><document /></el-icon>
          </div>
          <div class="card-right">
            <div class="card-num">{{ songListCount }}</div>
            <div>歌单数量</div>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-row :gutter="20">
    <el-col :span="12">
      <h3>用户性别比例</h3>
      <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="userSex"></el-card>
    </el-col>
    <el-col :span="12">
      <h3>歌曲类型</h3>
      <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="songStyle"></el-card>
    </el-col>
  </el-row>
  <el-row :gutter="20">
    <el-col :span="12">
      <h3>歌手性别比例</h3>
      <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="singerSex"></el-card>
    </el-col>
    <el-col :span="12">
      <h3>歌手国籍</h3>
      <el-card class="cav-info" shadow="hover" :body-style="{ padding: '0px' }" id="country"></el-card>
    </el-col>
  </el-row>
  </div>
</template>
<script lang="ts" setup>
// import { ref, reactive, getCurrentInstance } from "vue";
import { ref, reactive } from "vue";
import * as echarts from "echarts";
import { Mic, Document, User, Headset } from "@element-plus/icons-vue";
import { HttpManager } from "@/api/index";

// const { proxy } = getCurrentInstance();
const userCount = ref(0);
const songCount = ref(0);
const singerCount = ref(0);
const songListCount = ref(0);
const userSex = reactive({
  series: [
    {
      type: "pie",
      label:{
        show:true,
        formatter:"{b}:{d}%"
      },
      data: [
        {
          value: 0,
          name: "男",
          itemStyle:{color: '#3399FF'}
        },
        {
          value: 0,
          name: "女",
          itemStyle: { color: '#FF66CC' }
        },
      ],
    },
  ],
});
const songStyle = reactive({
  xAxis: {
    type: "category",
    data: ["华语", "粤语", "欧美", "日韩", "BGM", "轻音乐", "乐器"],
  },
  yAxis: {
    type: "value",
  },
  label:{
  show:true,
    position:"top",
    formatter:'{c}'
  },
  series: [
    {
      data: [0, 0, 0, 0, 0, 0, 0],
      type: "bar",
      barWidth: "20%",
      itemStyle:{color:'#54acfd'}
    },
  ],
});
const singerSex = reactive({
  series: [
    {
      type: "pie",
      label:{
        show:true,
        formatter:"{b}:{d}%"
      },
      data: [
        {
          value: 0,
          name: "男",
          itemStyle:{color:'#3399FF'}
        },
        {
          value: 0,
          name: "女",
          itemStyle: { color: '#FF66CC' }
        },
      ],
    },
  ],
});
const country = reactive({
  xAxis: {
    type: "category",
    data: [
      "中国",
      "韩国",
      "意大利",
      "新加坡",
      "美国",
      // "马来西亚",
      "西班牙",
      "日本",
    ],
  },
  yAxis: {
    type: "value",
  },
  label:{
    show:true,
    position:'top',
    formatter:'{c}'
  },
  series: [
    {
      data: [0, 0, 0, 0, 0, 0, 0, 0],
      type: "bar",
      barWidth: "20%",
      itemStyle:{color:'#54acfd'}
    },
  ],
});

function setSex(sex, arr) {
  let value = 0;
  const name = sex === 0 ? "男" : "女";
  for (let item of arr) {
    if (sex === item.sex) {
      value++;
    }
  }
  return { value, name };
}
import { onMounted } from 'vue'

onMounted(() => {
  getConsumer()
  getSongList()
  getSong()
  getSinger()
})



async function getConsumer() {
  // 1. 获取所有用户信息
  const result = await (HttpManager.getAllUser()) as ResponseBody

  // 2. 设置用户总数
  userCount.value = result.data.length

  // 3. 设置用户性别 filter（过滤）
  const womanCount = result.data.filter(c => c.sex == 0).length
  const manCount = result.data.filter(c => c.sex == 1).length
  console.log(womanCount)
  console.log(manCount)

  // 4. 用户修改数据   男性
  userSex.series[0].data[0].value = manCount
  userSex.series[0].data[1].value = womanCount

  // 5. 初始化 echarts
  const userSexDom = document.getElementById('userSex');
  const myChart = echarts.init(userSexDom);
  myChart.setOption(userSex);
}

// 获取歌曲总数
async function getSong() {
  try {
    const result = await HttpManager.getAllSong() as ResponseBody

    if (!result.success || !result.data) {
      console.error("获取歌曲失败：", result.message)
      return
    }

    // 设置歌曲总数
    songCount.value = result.data.length
  } catch (err: any) {
    console.error("请求歌曲列表异常：", err.message || JSON.stringify(err))
  }
}


//1.获取所有用户信息插入echarts

//2.1 获取所有歌曲
async function getSongList() {
  // 1、获取所有歌单信息
  const result = await (HttpManager.getAllSongList()) as ResponseBody

  // 2、设置歌单总数
  songListCount.value = result.data.length
  for (let i = 0; i < songStyle.xAxis.data.length; i++) {
    let count = 0;
    let style = songStyle.xAxis.data[i];

    for (let songlist of result.data) {
      if (songlist.style.includes(style)) {
        count++;
      }
    }

    // 修改数据
    songStyle.series[0].data[i] = count;
  }
// 初始化echarts
  const songStyleDom=document.getElementById('songStyle')
  const myChart=echarts.init(songStyleDom);
  myChart.setOption(songStyle)
}

// 获取歌手数量
async function getSinger() {
  const result = await HttpManager.getAllSinger() as ResponseBody;
  singerCount.value = result.data.length;

  // 1. 性别统计
  const womanCount = result.data.filter(c => c.sex == 0).length;
  const manCount = result.data.filter(c => c.sex == 1).length;
  singerSex.series[0].data[0].value = manCount;
  singerSex.series[0].data[1].value = womanCount;

  const dom = document.getElementById('singerSex');
  const myChart = echarts.init(dom);
  myChart.setOption(singerSex);

  // 2. 国籍统计
  const locationList = country.xAxis.data;
  const locationCount = new Array(locationList.length).fill(0);

  for (let singer of result.data) {
    const index = locationList.findIndex(loc => singer.location.includes(loc));
    if (index !== -1) {
      locationCount[index]++;
    }
  }

  for (let i = 0; i < locationCount.length; i++) {
    country.series[0].data[i] = locationCount[i];
  }

  const countryDom = document.getElementById("country");
  const countryChart = echarts.init(countryDom);
  countryChart.setOption(country);
}


//2.2 获取所有歌曲信息插入echarts

//3.获取所有歌手信息插入echarts

</script>

<style scoped>
.card-content {
  display: flex;
  align-items: center;
  justify-content: space-around;
  height: 100px;
  padding-left: 20%;
  text-align: center;
}

.card-left {
  display: flex;
  font-size: 3rem;
}

.card-right {
  flex: 1;
  font-size: 14px;
}

.card-num {
  font-size: 30px;
  font-weight: bold;
}

h3 {
  margin: 10px 0;
  text-align: center;
}
.cav-info {
  border-radius: 6px;
  overflow: hidden;
  height: 250px;
  background-color: white;
}
</style>
