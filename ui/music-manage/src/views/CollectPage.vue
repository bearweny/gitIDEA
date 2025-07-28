<template>
  <el-breadcrumb class="crumbs" separator="/">
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="{ path: item.path, query: item.query }">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>

  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input placeholder="筛选歌曲" v-model="keyword"></el-input>
      <el-button type="primary" @click="getData">查询</el-button>
    </div>
    <el-table height="550px" border size="small" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column prop="songName" label="歌手-歌曲"></el-table-column>
      <el-table-column label="操作" width="90" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 删除提示框 -->
  <yin-del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></yin-del-dialog>
</template>

<script lang="ts">
import { defineComponent, getCurrentInstance, watch, ref, computed } from "vue";
import { useStore } from "vuex";
import { HttpManager } from "@/api";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";
import { ElMessage } from 'element-plus'

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const { proxy } = getCurrentInstance();
    const store = useStore();

    const tableData = ref([]); // 记录歌曲，用于显示
    const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
    const breadcrumbList = computed(() => store.getters.breadcrumbList);

    const keyword = ref(""); // 记录输入框输入的内容
    watch(keyword, () => {
      //假搜索
    });

    getData();

    // 通过用户 ID 获取用户收藏的歌曲 ID
    async function getData() {
      tableData.value = [];
      const result = await HttpManager.getCollectionOfUser(proxy.$route.query.id, keyword.value) as ResponseBody;
      tableData.value = result.data;
    }




    /**
     * 删除
     */
    const idx = ref(-1); // 记录当前要删除的行
    const multipleSelection = ref([]); // 记录当前要删除的列表
    const delVisible = ref(false); // 显示删除框



    async function confirm() {
      const result = await (HttpManager.deleteCollect(idx.value)) as ResponseBody
      // 2. 提示信息
      (proxy as any).$message({
        message: result.message,
        type: result.success ? 'success' : 'error'
      })
      // 3. 刷新表格
      getData()

      delVisible.value = false;
    }

    function deleteRow(id) {
      idx.value = id;
      delVisible.value = true;
    }
    function handleSelectionChange(val) {
      multipleSelection.value = val;
    }
    async function deleteAll() {
      if (multipleSelection.value.length === 0) {
        ElMessage.warning("请先选择要删除的收藏");
        return;
      }

      const songIds = multipleSelection.value.map((item) => item.id);

      const userId = proxy.$route.query.id;

      try {
        await HttpManager.deleteBatchCollection(userId, songIds);
        ElMessage.success("批量删除成功");
        getData();
      } catch (error) {
        ElMessage.error("批量删除失败");
      }
    }


    return {
      getData,
      keyword,
      tableData,
      delVisible,
      breadcrumbList,
      deleteAll,
      handleSelectionChange,
      deleteRow,
      confirm,
    };
  },
});
</script>

<style scoped></style>
