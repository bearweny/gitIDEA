<template>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input placeholder="筛选歌手" v-model="keyword"></el-input>
      <el-button type="primary" @click="getData">查询</el-button>

      <el-button type="primary" @click="centerDialogVisible = true">添加歌手</el-button>
    </div>
    <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>
      <el-table-column label="歌手图片" prop="pic" width="110" align="center">
        <template v-slot="scope">
          <div class="singer-img">
            <img :src="attachImageUrl(scope.row.pic)" style="width: 100%" />
          </div>
          <el-upload
              :action="`http://localhost:8888/singer/avatar/${scope.row.id}`"
              :show-file-list="false"
              :on-success="handleImgSuccess"
              :before-upload="beforeImgUpload"
          >
            <el-button>更新图片</el-button>
          </el-upload>

        </template>
      </el-table-column>
      <el-table-column label="歌手" prop="name" width="120" align="center"></el-table-column>
      <el-table-column label="性别" prop="sex" width="60" align="center">
        <template v-slot="scope">
          <div>{{ changeSex(scope.row.sex) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="出生" prop="birth" width="120" align="center">
        <template v-slot="scope">
          <div>{{ getBirth(scope.row.birth) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="地区" prop="location" width="100" align="center"></el-table-column>
      <el-table-column label="简介" prop="introduction">
        <template v-slot="scope">
          <p style="height: 100px; overflow: scroll">
            {{ scope.row.introduction }}
          </p>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="120" align="center">
        <template v-slot="scope">
          <el-button @click="goSongPage(scope.row)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template v-slot="scope">
          <el-button @click="editRow(scope.row)">编辑</el-button>
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
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

  <!-- 添加 -->
  <el-dialog title="添加歌手" v-model="centerDialogVisible">
    <el-form label-width="80px" :model="registerForm" :rules="singerRule">
      <el-form-item label="歌手名" prop="name">
        <el-input v-model="registerForm.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="registerForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">保密</el-radio>
          <el-radio :label="2">组合</el-radio>
          <el-radio :label="3">不明</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="故乡" prop="location">
        <el-input v-model="registerForm.location"></el-input>
      </el-form-item>
      <el-form-item label="出生日期" prop="birth">
        <el-date-picker type="date" v-model="registerForm.birth"></el-date-picker>
      </el-form-item>
      <el-form-item label="歌手介绍" prop="introduction">
        <el-input type="textarea" v-model="registerForm.introduction"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addsinger">确 定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 编辑弹出框 -->
  <el-dialog title="编辑" v-model="editVisible">
    <el-form label-width="60px" :model="editForm" :rules="singerRule">
      <el-form-item label="歌手" prop="name">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="editForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">保密</el-radio>
          <el-radio :label="2">组合</el-radio>
          <el-radio :label="3">不明</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出生" prop="birth">
        <el-date-picker type="date" v-model="editForm.birth"></el-date-picker>
      </el-form-item>
      <el-form-item label="地区" prop="location">
        <el-input v-model="editForm.location"></el-input>
      </el-form-item>
      <el-form-item label="简介" prop="introduction">
        <el-input type="textarea" v-model="editForm.introduction"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 删除提示框 -->
  <yin-del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event"></yin-del-dialog>
</template>

<script lang="ts">
import { defineComponent, getCurrentInstance, watch, ref, reactive, computed } from "vue";
import mixin from "@/mixins/mixin";
import YinDelDialog from "@/components/dialog/YinDelDialog.vue";
import { HttpManager } from "@/api/index";
import { RouterName } from "@/enums";
import { getBirth } from "@/utils";
import {ElMessage} from "element-plus";

export default defineComponent({
  components: {
    YinDelDialog,
  },
  setup() {
    const { proxy } = getCurrentInstance();
    const { changeSex, routerManager, beforeImgUpload } = mixin();

    const tableData = ref([]); // 记录歌曲，用于显示
    const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
    const pageSize = ref(5); // 页数
    const currentPage = ref(1); // 当前页
    const total =ref(0);
    // 计算当前表格中的数据
    const data = computed(() => {
      // 假分页
      return tableData.value;
    });

    const keyword = ref(""); // 记录输入框输入的内容
    watch(keyword, () => {
      //假搜索
    });

    getData();

    async function getData() {
      tableData.value = [];
      // 发动请求
      const result=await (HttpManager.getAllSingerPage(currentPage.value, pageSize.value, keyword.value)) as ResponseBody
      tableData.value=result.data.records
      total.value=result.data.total
    }
    // 获取当前页
    function handleCurrentChange(val) {
      currentPage.value = val;
      getData()
    }
    function uploadUrl(id) {
      return HttpManager.attachImageUrl(`/singer/avatar/update?id=${id}`);
    }

    /**
     * 路由
     */
    function goSongPage(row) {
      //跳转 歌曲管路页面
      console.log(row)

      const breadcrumbList = reactive([
        {
          path: RouterName.Singer,
          name: "歌手管理",
        },
        {
          path: "",
          name: "歌曲信息",
        },
      ]);
      proxy.$store.commit("setBreadcrumbList", breadcrumbList);
      routerManager(RouterName.Song, {
        path: RouterName.Song,
        query: { id: row.id, name: row.name },
      });
    }

    /**
     * 添加
     */
    const centerDialogVisible = ref(false);
    const registerForm = reactive({
      name: "",
      sex: "",
      birth: new Date(),
      location: "",
      introduction: "",
    });
    const singerRule = reactive({
      name: [{ required: true, trigger: "change" }],
      sex: [{ required: true, trigger: "change" }],
    });

    async function addsinger() {
      //获取表单数据
      let datetime = getBirth(registerForm.birth);
      let id=null;
      let name = registerForm.name;
      let sex = registerForm.sex;
      let birth = datetime;
      let location = registerForm.location;
      let introduction = registerForm.introduction;
      //调用添加接口
      const result=await (HttpManager.setSinger({id,name,sex,birth,location,introduction})) as ResponseBody
      //提示信息
      (proxy as any).$message({
        message:result.message,
        type: result.success ? 'success' : 'error'
      })
      //清空新增表单，刷新表格
      registerForm.name= ""
      registerForm.sex= ""
      registerForm.birth=new Date()
      registerForm.location= ""
      registerForm.introduction= ""
      getData()
    }

    /**
     * 编辑
     */
    const editVisible = ref(false);
    const editForm = reactive({
      id: "",
      name: "",
      sex: "",
      pic: "",
      birth: new Date(),
      location: "",
      introduction: "",
    });

    function editRow(row) {
      editVisible.value = true;
      editForm.id = row.id;
      editForm.name = row.name;
      editForm.sex = row.sex;
      editForm.pic = row.pic;
      editForm.birth = row.birth;
      editForm.location = row.location;
      editForm.introduction = row.introduction;
    }
    async function saveEdit() {
      try {
        let birth = getBirth(new Date(editForm.birth));
        let id = editForm.id;
        let name = editForm.name;
        let sex = editForm.sex;
        let location = editForm.location;
        let introduction = editForm.introduction;

        // 调用编辑接口
        const result = await (HttpManager.setSinger({
          id, name, sex, birth, location, introduction
        })) as ResponseBody;

        // 提示信息
        (proxy as any).$message({
          message: result.message,
          type: result.success ? 'success' : 'error'
        });
      }catch (error) {
        console.error(error);
      }
      editVisible.value=false

      getData()
    }

    function handleImgSuccess(response, file) {
      (proxy as any).$message({
        message: response.message,
        type: response.type,
      });
      if (response.success) getData();
    }

    /**
     * 删除
     */
    const idx = ref(-1); // 记录当前要删除的行
    const multipleSelection = ref([]); // 记录当前要删除的列表
    const delVisible = ref(false); // 显示删除框

    async function confirm() {
      const res = await HttpManager.deleteSinger(idx.value) as ResponseBody;

      (proxy as any).$message({
        message: res.message,
        type: res.success ? 'success' : 'error'
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
        ElMessage.warning("请选择要删除的歌手")
        return;
      }
      const ids = multipleSelection.value.map((item) => item.id);
      try {
         await HttpManager.batchDeleteSinger(ids);
        ElMessage.success("批量删除成功");
          getData(); // 重新加载表格数据

      } catch (error) {
        console.error(error);
      }
    }


    return {
      total,
      getData,
      keyword,
      data,
      tableData,
      centerDialogVisible,
      editVisible,
      delVisible,
      pageSize,
      currentPage,
      registerForm,
      editForm,
      singerRule,
      deleteAll,
      handleSelectionChange,
      handleImgSuccess,
      beforeImgUpload,
      saveEdit,
      attachImageUrl: HttpManager.attachImageUrl,
      changeSex,
      getBirth,
      uploadUrl,
      goSongPage,
      editRow,
      handleCurrentChange,
      addsinger,
      confirm,
      deleteRow,
    };
  },
});
</script>

<style scoped>
.singer-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
