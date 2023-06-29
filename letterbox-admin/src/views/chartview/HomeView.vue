<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <div>
          <el-statistic group-separator="," :precision="2" :value="value2" :title="title"></el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic title="男女比">
            <template slot="formatter"> 456/2 </template>
          </el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic group-separator="," :precision="2" decimal-separator="." :value="value1" :title="title">
            <template slot="prefix">
              <i class="el-icon-s-flag" style="color: red"></i>
            </template>
            <template slot="suffix">
              <i class="el-icon-s-flag" style="color: blue"></i>
            </template>
          </el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic :value="like ? 521 : 520" title="Feedback">
            <template slot="suffix">
              <span @click="like = !like" class="like">
                <i class="el-icon-star-on" style="color:red" v-show="!!like"></i>
                <i class="el-icon-star-off" v-show="!like"></i>
              </span>
            </template>
          </el-statistic>
        </div>
      </el-col>
    </el-row>
    <el-divider><i class="el-icon-loading"></i></el-divider>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-carousel indicator-position="outside" height="60vh" style="border-radius: 10px;">
          <el-carousel-item v-for="item in fileList" :key="item.id">
            <el-image 
              style="width: 100%; height: 60vh"
              :src="item.url"
              fit="fill"
              :preview-src-list="srcList">
            </el-image>
          </el-carousel-item>
        </el-carousel>
      </el-col>
      <el-col :span="12">
        <el-row>
          <div ref="myEchart1" style="width: 100%; height: 30vh;"></div>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <div ref="myEchart2" style="width: 100%; height: 30vh;"></div>
        </el-row>
      </el-col>
    </el-row>
    <el-divider><i class="el-icon-star-off"></i></el-divider>
    <el-row :gutter="10">
      <el-col :span="12"><el-link href="https://github.com/JasonD0216" icon="el-icon-star-on"> Github </el-link></el-col>
      <el-col :span="12"><el-link href="https://www.bailublog.cn" type="primary" icon="el-icon-link"> Blog </el-link></el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "HomeView",
  data() {
    return {
      like: true,
      value1: 4154.564,
      value2: 2222,
      title: '今年的增长',
      fileList: [],
      srcList: []
    }
  },
  created() {
    this.load();
  },
  mounted() {
    this.initChart1();
    this.initChart2();
  },
  methods: {
    load() {
      this.axios.get("/banner/all").then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.fileList = result.data;
          this.fileList.forEach(item => {
            this.srcList.push(item.url);
          })
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    initChart1() {
      let myChart = echarts.init(this.$refs.myEchart1)
      let option = {
        xAxis: {
          type: 'category',
          data: [1, 2, 3, 4, 5, 6, 7]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
          }
        ]
      };
      myChart.setOption(option);
      window.addEventListener('resize', function() {
        myChart.resize();
      });
    },
    initChart2() {
      let myChart = echarts.init(this.$refs.myEchart2)
      let option = {
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.setOption(option);
      window.addEventListener('resize', function() {
        myChart.resize();
      });
    },
  }
}
</script>

<style scoped>

</style>
