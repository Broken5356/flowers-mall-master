<template>
  <div>
    <!--   操作面板   -->
    <el-card>
      <div>
        <h2 class="my-title">用户管理</h2>
      </div>
      <div class="search">
        <el-input v-model="param" style="width: 240px" @keyup.enter.native="refresh(1)"
                  placeholder="账号 / 姓名"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="refresh(1)">搜索</el-button>
      </div>
      <div class="create">
        <el-button type="success" @click="create">新建用户</el-button>
      </div>
    </el-card>
    <!--   表格面板   -->
    <el-card>
      <el-table
        :data="list"
      >
        <el-table-column
          label="#"
          type="index"
          align="center"
          width="50">
        </el-table-column>

        <el-table-column
          prop="account"
          label="账号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="name"
          label="姓名"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="password"
          label="密码"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="phone"
          label="电话"
          width="150"
          align="center"
        >
        </el-table-column>


        <el-table-column
          prop="address"
          label="地址"
          width="300"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-button type="primary" @click="update(scope.row)">修改</el-button>
            <el-popconfirm
                title="确定删除吗？"
                @confirm="doDelete"
              >
              <el-button type="danger" slot="reference" @click="del(scope.row.id)">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <!--   分页   -->
      <div>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="pagination.total"
          :page-size="pagination.pageSize"
          :current-page="pagination.currentPage"
          @current-change="pageChange">
        </el-pagination>
      </div>
    </el-card>
    <!--   修改   -->
    <div>
      <el-dialog
        title="修改用户信息"
        :visible.sync="updateDialogVisible"
        width="50%"
      >
        <div>
          <el-form :model="updateUser" label-width="80px" size="small">

            <el-form-item label="">
              <el-input class="small-input" type="hidden" v-model="updateUser.id"></el-input>
            </el-form-item>

            <el-form-item label="账号">
              <el-input class="small-input" readonly="readonly" v-model="updateUser.account"></el-input>
            </el-form-item>

            <el-form-item label="姓名">
              <el-input class="small-input" v-model="updateUser.name"></el-input>
            </el-form-item>

            <el-form-item label="密码">
              <el-input class="small-input" v-model="updateUser.password"></el-input>
            </el-form-item>

            <el-form-item label="手机号">
              <el-input class="small-input" type="number" v-model="updateUser.phone"></el-input>
            </el-form-item>

            <el-form-item label="地址">
              <el-input class="small-input" v-model="updateUser.address"></el-input>
            </el-form-item>

            <div class="create-dialog-btn">
              <el-button type="primary" @click="doUpdate">保存</el-button>
              <el-button type="warning" @click="cancel">取消</el-button>
            </div>

          </el-form>
        </div>
      </el-dialog>
    </div>
    <!--   新增   -->
    <div>
      <el-dialog
        title="新建用户"
        :visible.sync="createDialogVisible"
        width="50%"
      >
        <div>
          <el-form :model="addUser" label-width="80px" size="small">

            <el-form-item label="账号">
              <el-input class="small-input" v-model="addUser.account"></el-input>
            </el-form-item>

            <el-form-item label="姓名">
              <el-input class="small-input" v-model="addUser.name"></el-input>
            </el-form-item>

            <el-form-item label="密码">
              <el-input class="small-input" v-model="addUser.password"></el-input>
            </el-form-item>

            <el-form-item label="手机号">
              <el-input class="small-input" type="number" v-model="addUser.phone"></el-input>
            </el-form-item>

            <el-form-item label="地址">
              <el-input class="small-input" v-model="addUser.address"></el-input>
            </el-form-item>

            <div class="create-dialog-btn">
              <el-button type="primary" @click="doCreate">保存</el-button>
              <el-button type="warning" @click="cancel">取消</el-button>
            </div>

          </el-form>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        param: '',
        createDialogVisible: false,
        updateDialogVisible: false,
        updateUser: {
          id: '',
          account: '',
          name: '',
          password: '',
          phone: '',
          address: ''
        },
        addUser: {
          account: '',
          name: '',
          password: '',
          phone: '',
          address: ''
        },
        list: [],
        delId: 0,
        pagination: {
          total: 1,
          pageSize: 5,
          currentPage: 1,
        },
      }
    },
    methods: {
      cancel() {
        this.updateDialogVisible = false;
        this.createDialogVisible = false;
      },
      update(row) {
        this.updateDialogVisible = true;
        this.updateUser = row;
      },

      doUpdate() {
        this.$http.put("/user/update", this.updateUser).then(result => {
          let R = result.data; // R
          if (R.code === 2000) {
            this.updateDialogVisible = false;
            this.$message.success(R.msg);
            this.refresh(this.pagination.currentPage);
          } else {
            this.$message.error(R.msg);
          }
        })
      },
      del(id) {
        this.delId = id;
      },
      doDelete() {
        this.$http.delete("/user/delete?id=" + this.delId).then(result => {
          let R = result.data; // R
          if (R.code === 2000) {
            this.$message.success(R.msg);
            this.refresh(1);
          } else {
            this.$message.error(R.msg);
          }
        })
      },
      create() {
        this.reset();
        this.createDialogVisible = true;
      },

      verify(user) {
        if (user.account.length < 4 || user.password.length < 4 || user.name.length < 2 || user.phone.length !== 11 || user.address.length < 3) {
          this.$message.warning('表单格式不正确，请修改');
          return false;
        }
        return true;
      },

      reset() {
        this.addUser = {
          account: '',
          password: '',
          name: '',
          phone: '',
          address: ''
        };
      },

      doCreate() {
        let flag = this.verify(this.addUser);
        if (flag) {
          this.$http.post("/user/create", this.addUser).then(result => {
            let R = result.data; // R
            if (R.code === 2000) {
              this.$message.success(R.msg);
              this.createDialogVisible = false;
              this.refresh(1);
            } else {
              this.$message.error(R.msg);
            }
          });
        }
      },

      pageChange(page) {
        this.refresh(page);
      },

      refresh(page) {
        if (page === undefined) {
          page = 1;
        }
        //window.sessionStorage.getItem('token')
        this.$http.get("/user/find?page=" + page + "&searchKey=" + this.param).then(result => {
          let R = result.data;
          this.list = R.data.items;
          this.pagination.total = R.data.len * this.pagination.pageSize;
          this.pagination.currentPage = page;
        })
      },
    },
    created() {
      this.refresh();
    }
  }
</script>

<style scoped>
  .search-input {
    width: 300px;
  }

  .updateForm {
    width: 80%;
  }

  .btn-release {
    float: right;
    margin-top: 10px;
    margin-right: 50px;
  }

  .form-releaseTask {
    height: 300px;
  }

  .input-content-task {
    width: 500px;
  }

  .my-el-table {
    text-align: center;
  }
</style>
