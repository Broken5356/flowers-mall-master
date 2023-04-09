<template>
  <div>
    <!--   操作面板   -->
    <el-card>
      <div>
        <h2 class="my-title">商品管理</h2>
      </div>
      <div class="search">
        <el-input v-model="param" style="width: 240px" @keyup.enter.native="refresh(1)"
                  placeholder="名称"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="refresh(1)">搜索</el-button>
      </div>
      <div class="create">
        <el-button type="success" @click="create">新建商品</el-button>
      </div>
    </el-card>
    <!--   表格面板   -->
    <el-card>
      <el-table
        :data="list"
      >
        <el-table-column
          label="#"
          align="center"
          width="200">
          <template slot-scope="scope">
            <img v-if="scope.row.img_guid.indexOf('.jpg') === -1" src="static/imgs/img.jpg">
            <img v-if="scope.row.img_guid.indexOf('.jpg') !== -1" :src="scope.row.img_guid">
          </template>
        </el-table-column>

        <el-table-column
          prop="name"
          label="名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="species_name"
          label="分类"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="price"
          label="价格"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="detail"
          label="详细介绍"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="species_name"
          label="状态"
          align="center"
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
          width="300px"
        >
          <template slot-scope="scope">
            <el-button type="primary" @click="update(scope.row)">修改</el-button>
            <el-button type="success" @click="updateImg(scope.row)">更新图片</el-button>
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
    <!--   新增   -->
    <div>
      <el-dialog
        title="新建商品"
        :visible.sync="createDialogVisible"
        width="50%"
      >
        <div>
          <el-form :model="addFlower" label-width="80px" size="small">

            <el-form-item label="名称">
              <el-input class="small-input" v-model="addFlower.name"></el-input>
            </el-form-item>

            <el-form-item label="分类">
              <el-select v-model="addFlower.species_name" placeholder="请选择">
                <el-option
                  v-for="item in species"
                  :label="item.species_name"
                  :value="item.species_name"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="价格">
              <el-input class="small-input" type="number" v-model="addFlower.price"></el-input>
            </el-form-item>

            <el-form-item label="详情">
              <el-input class="small-input" v-model="addFlower.detail"></el-input>
            </el-form-item>

            <div class="create-dialog-btn">
              <el-button type="primary" @click="doCreate">保存</el-button>
              <el-button type="warning" @click="cancel">取消</el-button>
            </div>

          </el-form>
        </div>
      </el-dialog>
    </div>
    <!--   修改   -->
    <div>
      <el-dialog
        title="修改商品信息"
        :visible.sync="updateDialogVisible"
        width="50%"
      >
        <div>
          <el-form :model="updateFlower" label-width="80px" size="small">
            <el-form-item label="名称">
              <el-input class="small-input" v-model="updateFlower.name"></el-input>
            </el-form-item>
            <el-form-item label="分类">
              <el-select v-model="updateFlower.species_name" placeholder="请选择">
                <el-option
                  v-for="item in species"
                  :label="item.species_name"
                  :value="item.species_name"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="价格">
              <el-input class="small-input" type="number" v-model="updateFlower.price"></el-input>
            </el-form-item>

            <el-form-item label="详情">
              <el-input class="small-input" v-model="updateFlower.detail"></el-input>
            </el-form-item>

            <div class="create-dialog-btn">
              <el-button type="primary" @click="doUpdate">保存</el-button>
              <el-button type="warning" @click="cancel">取消</el-button>
            </div>
          </el-form>
        </div>
      </el-dialog>
    </div>
<!--  更新商品图片  -->
    <div>
      <el-dialog
        title="更新图片"
        :visible.sync="imgDialogVisible"
        width="50%"
      >
        <div>
          <el-form :model="addFlower" label-width="80px" size="small">

<!--            <el-form-item label="商品图片">-->
<!--              <input type="file" @change="updateFile($event)">-->
<!--            </el-form-item>-->
            <el-upload
              class="upload-demo"
              action="http://localhost:8081/flower/updateImg"
              :on-change="handleChange"
              :on-success="uploadImgOK"
              :file-list="fileList"
              accept=".jpg,.jpeg,.png,.gif,.bmp,.pdf,.JPG,.JPEG,.PBG,.GIF,.BMP,.PDF"
            >
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>

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
        fileList: [],
        updateImgId: 0,
        param: '',
        imgDialogVisible: false,
        updateDialogVisible: false,
        createDialogVisible: false,
        // 种类
        species:[
          {
            id: 1,
            species_name: '春日'
          },
          {
            id: 1,
            species_name: '夏日'
          },
          {
            id: 1,
            species_name: '秋日'
          }
          ],
        updateFlower: {
          id: '',
          name: '',
          species_name: '',
          price: '',
          detail: '',
        },
        addFlower: {
          name: '',
          species_name: '',
          price: '',
          detail: '',
        },
        leader_accounts: [],
        list: [],
        delId: 0,
        pagination: {
          total: 1,
          pageSize: 5,
          currentPage: 1,
        },
        file: '',
      }
    },
    methods: {
      handleChange(file, fileList) {
        // this.$refs
        // this.fileList = fileList.slice(0);
      },
      // 上传图片成功，回调 {code: 2000
      // data: "1a8b9301f2744833ad48529fba672198.jpg"
      // msg: "更新图片成功"}
      uploadImgOK(response, file, fileList) {
        this.$http.put("/flower/updateImgGuid?guid="+response.data + "&id="+this.updateImgId).then(result => {
          let R = result.data; // R
          this.imgDialogVisible = false;
          if (R.code === 2000) {
            this.$message.success(R.msg);
            this.refresh(this.pagination.currentPage);
          } else {
            this.$message.error(R.msg);
          }
        })
      },
      // updateFile(event) {
      //   let file = event.target.files;
      //   event.preventDefault();
      //   let formData = new FormData();
      //   formData.append('id',this.updateImgId);
      //   formData.append('file', file);
      //   let config = {
      //     headers: {
      //       'Content-Type': 'multipart/form-data'
      //     }
      //   };
      //   this.$http.post('/flower/updateImg', formData, config).then(function (response) {
      //     let R = result.data; // R
      //     if (R.code === 2000) {
      //       this.$message.success(R.msg);
      //       this.refresh(this.pagination.currentPage);
      //     } else {
      //       this.$message.error(R.msg);
      //   }});
      //   this.imgDialogVisible = false;
      //   // (利用console.log输出看file文件对象)
      //   // let file = event.target.files;
      //   // console.log(file);
      //   // this.file = file;
      //   // do something...
      // },
      cancel() {
        this.imgDialogVisible = false;
        this.updateDialogVisible = false;
        this.createDialogVisible = false;
      },
      updateImg(row) {
        this.updateImgId = row.id;
        this.imgDialogVisible = true;
      },
      changeState(row) {
        let obj = {
          id: row.id,
          state: row.state===0?1:0
        };
        this.$http.put("/flower/changeState", obj).then(result => {
          let R = result.data; // R
          if (R.code === 2000) {
            this.$message.success(R.msg);
            this.refresh(this.pagination.currentPage);
          } else {
            this.$message.error(R.msg);
          }
        })
      },
      update(row) {
        this.updateDialogVisible = true;
        this.updateFlower.id = row.id;
        this.updateFlower.name = row.name;
        this.updateFlower.price = row.price;
        this.updateFlower.species_name = row.species_name;
        this.updateFlower.detail = row.detail;
      },

      doUpdate() {
        this.$http.put("/flower/update", this.updateFlower).then(result => {
          let R = result.data; // R
          if (R.code === 2000) {
            this.$message.success(R.msg);
            this.refresh(this.pagination.currentPage);
          } else {
            this.$message.error(R.msg);
          }
          this.updateDialogVisible = false;
        })
      },
      del(id) {
        this.delId = id;
      },
      doDelete() {
        this.$http.delete("/flower/delete?id=" + this.delId).then(result => {
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
        this.$http.get("/species/findAll").then(result => {
          let R = result.data;
          this.species = R.data;
        });
        this.createDialogVisible = true;
      },

      verify(flower) {
        if (flower.name.length < 2 || flower.species_name === undefined || flower.price.length < 1 || flower.detail.length < 2) {
          this.$message.warning('商品名称至少为 2 个字符，请修改');
          return false;
        }
        return true;
      },

      reset() {
        this.addFlower = {
          id: '',
          name: '',
          price: '',
          detail: '',
        };
      },

      doCreate() {
        console.log(this.addFlower);
        let flag = this.verify(this.addFlower);
        if (flag) {
          this.$http.post("/flower/create", this.addFlower).then(result => {
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
        this.$http.get("/flower/findAll?page=" + page + "&searchKey=" + this.param).then(result => {
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
  img{
    width: 200px;
    height: 200px;
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

  .my-el-table {
    text-align: center;
  }
  .el-select{
    margin-left: -53%;
  }
</style>
