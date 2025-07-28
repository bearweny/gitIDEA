<template>
  <div style="background-color: #ffe6f0; min-height: 100vh; padding: 20px;">
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input v-model="keyword" placeholder="筛选用户"></el-input>
      <el-button type="primary" @click="getData">查询</el-button>
    </div>

    <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>
      <el-table-column label="用户头像" width="102" align="center">
        <template v-slot="scope">
          <img :src="attachImageUrl(scope.row.avator)" style="width: 80px" />
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username" width="80" align="center"></el-table-column>
      <el-table-column label="性别" width="50" align="center">
        <template v-slot="scope">
          <div>{{ changeSex(scope.row.sex) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" prop="phoneNum" width="120" align="center"></el-table-column>
      <el-table-column label="邮箱" prop="email" width="120" align="center"></el-table-column>
      <el-table-column label="生日" width="120" align="center">
        <template v-slot="scope">
          <div>{{ getBirth(scope.row.birth) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="签名" prop="introduction"></el-table-column>
      <el-table-column label="地区" prop="location" width="70" align="center"></el-table-column>
      <el-table-column label="收藏" width="90" align="center">
        <template v-slot="scope">
          <el-button @click="goCollectPage(scope.row.id)">收藏</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="90" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column label="修改" width="90" align="center">

        <template v-slot="scope">
          <el-upload
              :action="'http://localhost:8888/consumer/avatar/' + scope.row.id"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <el-button>更新头像</el-button>
          </el-upload>


</template>


      </el-table-column>
    </el-table>
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

  <!-- 删除提示框 -->
  <yin-del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></yin-del-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, getCurrentInstance, watch, ref, reactive, computed } from "vue";
import mixin from "@/mixins/mixin";
import { HttpManager } from "@/api";
import { RouterName } from "@/enums";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";
import { getBirth } from "@/utils";

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const { proxy } = getCurrentInstance();
    const { changeSex, routerManager } = mixin();

    const tableData = ref([]); // 记录用户，用于显示
    const tempDate = ref([]); // 记录用户，用于搜索时能临时记录一份用户列表
    const pageSize = ref(5); // 页数
    const currentPage = ref(1); // 当前页
    const total = ref(0)

    // 计算当前表格中的数据
    const data = computed(() => {
      //假分页
      return tableData.value
    });

    const keyword = ref(""); // 记录输入框输入的内容
    watch(keyword, () => {
      //假搜索
    });

    getData();

    // 获取用户信息
    async function getData() {
      tableData.value = [];
      // 发起请求
      const result = await (HttpManager.getAllUserPage(currentPage.value, pageSize.value, keyword.value)) as ResponseBody;
      // 表格数据赋值
      tableData.value = result.data.records;
      total.value = result.data.total;
    }
    // 获取当前页
    function handleCurrentChange(val) {
      currentPage.value = val;
      getData()
    }

    /**
     * 路由
     */
    function goCollectPage(id) {
      console.log(id)
      const breadcrumbList = reactive([
        {
          path: RouterName.Consumer,
          name: "用户管理",
        },
        {
          path: "",
          name: "收藏信息",
        },
      ]);
      proxy.$store.commit("setBreadcrumbList", breadcrumbList);
      routerManager(RouterName.Collect, { path: RouterName.Collect, query: { id } });
    }

    /**
     * 删除
     */
    const idx = ref(-1); // 记录当前要删除的行
    const multipleSelection = ref([]); // 记录当前要删除的列表
    const delVisible = ref(false); // 显示删除框

    async function confirm() {
      // 1. 发送请求
      const result = await (HttpManager.deleteUser(idx.value)) as ResponseBody

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
    // 批量删除用户方法
    async function deleteAll() {
      // 1. 获取选中行的 id 数组
      const ids = multipleSelection.value.map(c => c.id)

      // 2. 发起 DELETE 请求，传 JSON 请求体（注意不要 .join("")）
      const result = await HttpManager.batchDeleteUser(ids) as ResponseBody

      // 3. 弹出提示信息
      (proxy as any).$message({
        message: result.message,
        type: result.success ? 'success' : 'error'
      })

      // 4. 刷新表格
      getData()

      // 5. 清空多选项
      multipleSelection.value = []
    }



    //handleAvatarSuccess
    function handleAvatarSuccess(){
      location.reload();
    }

    function beforeAvatarUpload(file) {
      const ltCode = 2;
      const isLt10M = file.size / 1024 / 1024;
      const uploadTypes = ref(["jpeg", "jpg", "png"]);

      const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

      if (isLt10M > ltCode || isLt10M <= 0) {
        (proxy as any).$message.error(`图片大小范围是 0~${ltCode}MB!`);
      }
      if (!isExistFileType) {
        (proxy as any).$message.error(`图片只支持 ${uploadTypes.value.join("、")} 格式！`);
      }

      return isLt10M <= ltCode && isLt10M > 0 && isExistFileType;
    }



    return {
      beforeAvatarUpload,
      getData,
      total,
      keyword,
      data,
      tableData,
      delVisible,
      pageSize,
      currentPage,
      deleteAll,
      handleSelectionChange,
      handleCurrentChange,
      changeSex,
      getBirth,
      deleteRow,
      handleAvatarSuccess,
      confirm,
      goCollectPage,
      attachImageUrl: HttpManager.attachImageUrl,
    };
  },
});
</script>

<style scoped></style>
