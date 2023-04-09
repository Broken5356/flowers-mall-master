<template>
  <div>
    <!--   操作面板   -->
    <el-card>
      <div>
        <h2 class="my-title">订单管理</h2>
      </div>
      <div class="search">
        <el-input v-model="searchKey" style="width: 240px" @keyup.enter.native="refresh(1)"
                  placeholder="商品名称"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="refresh(1)">搜索</el-button>
      </div>
    </el-card>
    <!--   表格面板   -->
    <el-card>
      <el-table
        :data="list"
        class="my-el-table"
      >
        <el-table-column
          label="#"
          type="index"
          width="150">
        </el-table-column>

        <el-table-column
          prop="username"
          label="买家"
        >
        </el-table-column>

        <el-table-column
          prop="flower"
          label="商品"
        >
        </el-table-column>

        <el-table-column
          prop="amount"
          label="数量"
        >
        </el-table-column>

        <el-table-column
          prop="price"
          label="价格(元)"
        >
        </el-table-column>

        <el-table-column
          prop="phone"
          label="联系电话"
        >
        </el-table-column>

        <el-table-column
          prop="address"
          label="地址"
        >
        </el-table-column>

        <el-table-column
          label="状态"
        >
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.state === 1"
              @change="changeState(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <el-popconfirm
              title="确定删除吗？"
              @confirm="doDelete"
            >
              <el-button type="danger" slot="reference" @click="del(scope.row.id)">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
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
  </div>
</template>

<script>
  export default {
    data() {
      return {
        searchKey: '',
        accounts: [],
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
      pageChange(page) {
        this.refresh(page);
      },

      changeState(order) {
        let obj = {
          id: order.id,
          state: order.state===0?1:0
        };
        this.$http.put("/order/changeState", obj).then(result => {
          let R = result.data; // R
          if (R.code === 2000) {
            this.$message.success(R.msg);
            this.refresh(this.pagination.currentPage);
          } else {
            this.$message.error(R.msg);
          }
        })
      },

      refresh(page) {
        if (page === undefined) {
          page = 1;
        }
        this.$http.get("/order/findAll?page=" + page + "&searchKey=" + this.searchKey ).then(result => {
          let R = result.data;
          this.list = R.data.items;
          this.pagination.total = R.data.len;
          this.pagination.currentPage = page;
        })
      },
      del(id) {
        this.delId = id;
      },
      doDelete() {
        this.$http.delete("/order/delete?id=" + this.delId).then(result => {
          let R = result.data;
          this.$message.success(R.msg);
          this.refresh(1);
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
</style>
