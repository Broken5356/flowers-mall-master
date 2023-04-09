<template>
  <div>
    <!--   操作面板   -->
    <el-card>
      <div>
        <h2 class="my-title" style="margin-bottom: 20px">我的购物车</h2>
        <el-popconfirm
          :title="orderMsg"
          @confirm="doBuy"
        >
          <el-button class="buy" type="primary" slot="reference" @click="buy()">下单</el-button>
        </el-popconfirm>
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

    </el-card>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        list: [],
        orderMsg: '',
        delId: 0,
      }
    },
    methods: {

      pageChange(page) {
        this.refresh(page);
      },

      refresh(page) {
        if (page === undefined) {
          page = 1;
        }
        //window.sessionStorage.getItem('token')
        this.$http.get("/cart/queryByAccount?account=" + window.sessionStorage.getItem('token')).then(result => {
          let R = result.data;
          this.list = R.data;
        })
      },
      del(id){
        this.delId = id;
      },
      buy(){
        let amount = 0;
        for (let i = 0; i < this.list.length; i++) {
          amount = amount + this.list[i].price;
        }
        this.orderMsg = '总计 ' + amount +' 元，确认购买？';
      },
      doDelete(){
        this.$http.delete("/cart/delete?id=" + this.delId).then(result => {
          let R = result.data;
          this.$message.success(R.msg);
          this.refresh(1);
        })
      },
      doBuy(){
        this.$http.get("/cart/buy?account=" + window.sessionStorage.getItem("token")).then(result => {
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
  .buy{
    float: right;
    margin-right: 20px;
    margin-bottom: 10px;
  }
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
