<template>
  <div class="play-list-container">
    <yin-nav :styleList="songStyle" :activeName="activeName" @click="handleChangeView"></yin-nav>
    <play-list :playList="data" path="song-sheet-detail"></play-list>
    <el-pagination
      class="pagination"
      background
      layout="total, prev, pager, next"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from "vue";
import YinNav from "@/components/layouts/YinNav.vue";
import PlayList from "@/components/PlayList.vue";
import { SONGSTYLE } from "@/enums";
import { HttpManager } from "@/api";

export default defineComponent({
  components: {
    YinNav,
    PlayList,
  },
  setup() {
    const activeName = ref("全部歌单");
    const pageSize = ref(15); // 页数
    const currentPage = ref(1); // 当前页
    const songStyle = ref(SONGSTYLE); // 歌单导航栏类别
    const data = ref([]);
    const total =ref(0);

    // 获取全部歌单
    // 通过类别获取歌单
// 通过类别获取歌单
    async function getSongListOfStyle(style) {
      // 根据style获取歌单分页查询
      const result = (await HttpManager.getSongListOfStyle(style, currentPage.value, pageSize.value)) as ResponseBody;
      data.value = result.data.records;
      total.value = result.data.total;
    }


    try {
      getSongListOfStyle("");
    } catch (error) {
      console.error(error);
    }

    // 获取歌单
    async function handleChangeView(item) {
      activeName.value = item.name;
      try {
        if (item.name === "全部歌单") {
          await getSongListOfStyle("");
        } else {
          await getSongListOfStyle(item.name);
        }
      } catch (error) {
        console.error(error);
      }
    }
    // 获取当前页
    async function handleCurrentChange(val) {
      currentPage.value = val;
      if (activeName.value === "全部歌单") {
        await getSongListOfStyle("");
      } else {
        await getSongListOfStyle(activeName.value);
      }
    }

    return {
      total,
      activeName,
      songStyle,
      pageSize,
      currentPage,
      data,
      handleChangeView,
      handleCurrentChange,
    };
  },
});
</script>
