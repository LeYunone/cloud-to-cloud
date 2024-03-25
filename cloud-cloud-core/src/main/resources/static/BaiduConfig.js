Vue.component('baidu-config', {
    template: `
    <div>
      <div>
        <h2>百度配置页面</h2>
        <div class="tab-content">百度配置内容</div>
        <el-tabs type="border-card">
            <el-tab-pane label="产品映射">
                <el-button type="primary" @click="productTypeDialog=true">新增</el-button>
                <el-table :data="productTypeTable" style="width: 100%">
                    <el-table-column prop="id" label="序号"></el-table-column>
                    <el-table-column prop="productId" label="开发云产品ID"></el-table-column>
                    <el-table-column prop="thirdProductId" label="产商云产品ID"></el-table-column>
                    <el-table-column prop="thirdProduct" label="产商云产品名"></el-table-column>
                    <el-table-column prop="thirdProduct2" label="产品名 - 第二形式"></el-table-column>
                    <el-table-column prop="thirdBrand" label="产商云支持品牌"></el-table-column>
                    <el-table-column label="操作">
                      <template slot-scope="scope">
                        <el-button type="text" @click="productTypeEdit(scope.row.id)">编辑</el-button>
                        <el-button type="text" @click="productTypeDelete(scope.row.id)">删除</el-button>
                      </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                         @size-change="productTypeHandleSizeChange"
                         @current-change="productTypeHandleCurrentChange"
                         :current-page="currentPage"
                         :page-sizes="[10, 20, 30, 40]"
                         :page-size="pageSize"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="total">
                </el-pagination>
            </el-tab-pane>
            <el-tab-pane label="属性映射">
                
            </el-tab-pane>
        </el-tabs>
        
       
      </div>

    <el-dialog title="产品映射表单" :visible.sync="productTypeDialog" before-close="productTypeCanel">
    <el-table
      :data="productType.productTypes"
      style="width: 100%">
      <el-table-column label="产商云产品ID" prop="thirdProductId">
        <template slot-scope="scope">
          <el-input v-model="scope.row.vendorCloudProductId"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="第三方产品名" prop="thirdProduct">
        <template slot-scope="scope">
          <el-input v-model="scope.row.thirdProduct"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="第三方产品名 第二形式" prop="thirdProduct2">
        <template slot-scope="scope">
          <el-input v-model="scope.row.thirdProduct2"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="第三方品牌" prop="thirdBrand">
        <template slot-scope="scope">
          <el-input v-model="scope.row.thirdBrand"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="removeRow(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button @click="addRow">新增行</el-button>
    <span slot="footer" class="dialog-footer">
      <el-button @click="productTypeCanel">取消</el-button>
      <el-button type="primary" @click="productTypeSave">保存</el-button>
    </span>
    </el-dialog>
    </div>
  `,
    mounted: function () {
        this.baiduProductType();
    },
    data: function () {
        return {
            productTypeTable: [],
            currentPage: 1, // 当前页数
            pageSize: 10, // 每页显示条数
            total: 0,// 总条数
            productTypeDialog: false,//产品映射弹窗
            productType: {
                productId:"",
                thirdPartyCloud:"BAIDU",
                productTypes:[
                    {
                        id: "",
                        thirdProduct: "",
                        thirdProduct2: "",
                        thirdProductId: "",
                        thirdBrand: ""
                    }
                ]
            },
            thirdPids:[]
        }
    }
    ,
    methods: {
        productTypeThirdIds(){
            axios({
                url: "/toc/api/web/productType/thirdProducts",
                params: {
                    thirdPartyCloud: "BAIDU"
                }
            }).then((res) => {
                this.thirdPids = res.data.result;
            })
        }
        ,
        productTypeHandleSizeChange(size) {
            this.pageSize = size;
            this.baiduProductType();
        },
        productTypeHandleCurrentChange(index) {
            this.currentPage = index;
            this.baiduProductType();
        },
        baiduProductType() {
            axios({
                url: "/toc/api/web/productType/list",
                params: {
                    index: this.currentPage,
                    size: this.pageSize,
                    thirdPartyCloud: "BAIDU"
                }
            }).then((res) => {
                var result = res.data.result;
                this.total = result.total;
                this.productTypeTable = result.records;
            })
        },
        productTypeEdit(id) {
            axios({
                url: "/toc/api/web/productType/detail",
                params: {
                    id: id
                }
            }).then((res) => {
                this.productType = res.data.result;
                this.productTypeDialog = true;
            })
        },
        productTypeSave() {
            axios({
                method: "POST",
                url: "/toc/api/web/productType/save",
                data: {
                    id: this.productType.id,
                    productId: this.productType.productId,
                    thirdProduct: this.productType.thirdProduct,
                    thirdProduct2: this.productType.thirdProduct2,
                    thirdProductId: this.productType.thirdProductId,
                    thirdBrand: this.productType.thirdBrand,
                    thirdPartyCloud: "BAIDU"
                }
            }).then((res) => {
                this.productTypeDialog = false;
                this.productType = this.$options.data().productType;
                this.baiduProductType();
            })
        },
        productTypeDelete() {

        },
        productTypeCanel() {
            this.productTypeDialog = false;
            this.productType = this.$options.data().productType;
        },
    }
});