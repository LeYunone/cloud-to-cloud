Vue.component("tmall-config", {
    template: `
    <div>
        <div>
            <div style="text-align: center; font-weight: bold; position: relative; top: -5px;">< 天猫精灵配置页面 ></div>
            <el-row >
              <el-col >
                <el-card shadow="always">
                    <el-form :inline="true" ref="form" :model="clientConfig" label-width="125px">
                      <el-form-item label="客户端id">
                        <el-input v-model="clientConfig.clientId"></el-input>
                      </el-form-item>
                      <el-form-item label="客户端秘钥">
                        <el-input v-model="clientConfig.clientSecret"></el-input>
                      </el-form-item>
                      <el-form-item label="目标项目路由">
                        <el-input v-model="clientConfig.mainUrl"></el-input>
                      </el-form-item>
                      <el-form-item label="图标">
                        <el-input v-model="clientConfig.icon"></el-input>
                      </el-form-item>
                      <el-form-item label="附加信息">
                        <el-input v-model="clientConfig.additionalInformation"></el-input>
                      </el-form-item>
                      <el-form-item label="上报URL">
                        <el-input v-model="clientConfig.reportUrl"></el-input>
                      </el-form-item>
                      <el-form-item size="large">
                        <el-button type="primary" @click="clientConfigSave">保存</el-button>
                      </el-form-item>
                    </el-form>
                </el-card>
              </el-col>
            </el-row>
            
            <el-tabs v-model="panelActiveName" type="border-card" @tab-click="paneHandler">
                <el-tab-pane name="productType" label="产品映射">
                <el-form :inline="true"  class="demo-form-inline">
                  <el-form-item label="开发云产品id">
                    <el-input v-model="queryFrom.productId" placeholder="产品id"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="tmallProductType">查询</el-button>
                    <el-button style="text-align: right;" type="primary" @click="productTypeDialog = true">新增</el-button>
                  </el-form-item>
                </el-form>
                    <el-table :data="productTypeTable" style="height:500px;overflow: auto;width: 100%">
                        <el-table-column prop="productId" label="开发云产品ID"></el-table-column>
                        <el-table-column prop="thirdProductIds" label="产商云产品ID">
                            <template slot-scope="scope">
                                <span v-html="scope.row.thirdProductIds.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="thirdPartyCloud" label="产商云"></el-table-column>
                        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" @click="productTypeEdit(scope.row.productId)">编辑</el-button>
                                <el-button type="text" @click="productTypeDelete(scope.row.productId)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="productTypeHandleSizeChange"
                            @current-change="productTypeHandleCurrentChange"
                            :current-page="productTypeCurrentPage"
                            :page-sizes="[10, 20, 30, 40]"
                            :page-size="productTypePageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :productTypeTotal="productTypeTotal"
                    >
                    </el-pagination>
                </el-tab-pane>
            </el-tabs>
        </div>

        <!--产品映射表-->
        <el-dialog title="产品映射表单" :visible.sync="productTypeDialog" :before-close="productTypeCancel">
            <el-form ref="productType" :model="productType" label-width="100px">
                <el-form-item label="开发云产品id">
                    <el-input v-model="productType.productId" :disabled="isProductTypeUpdate"></el-input>
                </el-form-item>
                <el-table :data="productType.productTypes" style="width: 100%">
                    <el-table-column label="产商云产品ID" prop="thirdProductId">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.thirdProductId" placeholder="请选择" clearable filterable>
                                <el-option
                                        v-for="item in thirdPids"
                                        :key="item"
                                        :label="item"
                                        :value="item"
                                ></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="productTypeRemoveRow(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <el-button @click="productTypeAddRow">新增行</el-button>
            <span slot="footer" class="dialog-footer">
          <el-button @click="productTypeCancel">取消</el-button>
          <el-button type="primary" @click="productTypeSave">保存</el-button>
        </span>
        </el-dialog>

        
    </div>
  `,
    mounted: function () {
        this.tmallProductType();
        this.productTypeThirdIds();
        this.clientConfigDetail();
        axios({
            url: "/cloudToCloud/api/web/config/convertFunctions",
        }).then((res) => {
            this.convertFunctions = res.data.result;
        });
        axios({
            url: "/cloudToCloud/api/web/config/capability",
            params: {
                cloud: "TMALL"
            }
        }).then((res) => {
            this.deviceCapabilities = res.data.result;
        })
    },
    data: function () {
        return {
            queryFrom: {
                productId: ""
            },
            clientConfig: {
                clientId: "",
                clientSecret: "",
                mainUrl: "",
                icon: "",
                additionalInformation: "",
                reportUrl: "",
                thirdPartyCloud: "TMALL",
                thirdClientId: "",
                thirdClientSecret: ""
            },
            isProductTypeUpdate: false,
            productTypeTable: [],
            productTypeCurrentPage: 1, // 当前页数
            productTypePageSize: 10, // 每页显示条数
            productTypeTotal: 0, // 总条数
            productTypeDialog: false, //产品映射弹窗
            productType: {
                productId: "",
                thirdPartyCloud: "TMALL",
                productTypes: [
                    {
                        id: "",
                        thirdProduct: "",
                        thirdProduct2: "",
                        thirdProductId: "",
                        thirdBrand: "",
                    },
                ],
            },

            isProductFunctionUpdate: false,
            productFunctionTable: [],
            productFunctionCurrentPage: 1, // 当前页数
            productFunctionPageSize: 10, // 每页显示条数
            productFunctionTotal: 0, // 总条数
            productFunctionDialog: false, //产品映射弹窗
            productFunction: {
                productId: "",
                thirdPartyCloud: "TMALL",
                productFunctions: [
                    {
                        id: "",
                        signCode: "",
                        functionId: "",
                        thirdSignCode: "",
                        valueOf: false,
                        valueMapping: [],
                        convertFunction: "",
                        capabilityConfigId: [],
                        thirdActionCode: "",
                        remark: ""
                    },
                ],
            },

            isProductActionUpdate: false,
            productActionTable: [],
            productActionCurrentPage: 1, // 当前页数
            productActionPageSize: 10, // 每页显示条数
            productActionTotal: 0, // 总条数
            productActionDialog: false, //产品映射弹窗
            productAction: {
                productId: "",
                thirdPartyCloud: "TMALL",
                productActions: [
                    {
                        id: "",
                        signCode: "",
                        functionId: "",
                        thirdSignCode: "",
                        valueOf: false,
                        valueMapping: [],
                        thirdActionCode: "",
                        convertFunction: "",
                        remark: ""
                    },
                ],
            },

            thirdPids: [],
            convertFunctions: [],
            deviceCapabilities: [],
            panelActiveName: "productType",

            productActionEditDialog: false,
            productActionEditPanelFrom: {
                index: 1,
                signCode: "",
                functionId: "",
                thirdSignCode: "",
                valueOf: false,
                valueMapping: [],
                convertFunction: "",
                remark: ""
            },
            productFunctionEditDialog: false,
            productFunctionEditPanelFrom: {
                index: 1,
                signCode: "",
                functionId: "",
                thirdSignCode: "",
                valueOf: false,
                valueMapping: [],
                convertFunction: "",
                capabilityConfigId: [],
                remark: "",
                thirdActionCode:""
            }
        };
    },
    methods: {
        changeStatus(e, row) {
            row.valueOf = e;
        },
        formatJson(row) {
            try {
                row.thirdSignCode = JSON.stringify(JSON.parse(row.thirdSignCode), null, 2);
            } catch (error) {
                // JSON 解析失败，不进行格式化操作
            }
        },
        paneHandler(tab, event) {
            this.queryFrom.productId = "";
            if (this.panelActiveName === "productType") {
                this.tmallProductType();
            } else if (this.panelActiveName === "productFunction") {
                this.tmallProductFunction();
            } else if (this.panelActiveName === "productAction") {
                this.tmallProductAction();
            } else if (this.panelActiveName === "productCapability") {
                this.tmallProductCapability();
            }
        },
        clientConfigDetail() {
            axios({
                url: "/cloudToCloud/api/web/config/clientConfig",
                params: {
                    "cloud": "TMALL"
                }
            }).then((res) => {
                this.clientConfig = res.data.result;
            })
        },
        clientConfigSave() {
            axios({
                url: "/cloudToCloud/api/web/config/saveClientConfig",
                method: "POST",
                data: this.clientConfig
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.$message.success("保存成功");
                } else {
                    this.$message.error(result.message);
                }
            })
        },

        //产品类型方法
        productTypeRemoveRow(index) {
            this.productType.productTypes.splice(index, 1);
        },
        productTypeAddRow() {
            this.productType.productTypes.push({});
        },
        productTypeThirdIds() {
            axios({
                url: "/cloudToCloud/api/web/productType/thirdProducts",
                params: {
                    thirdPartyCloud: "TMALL",
                },
            }).then((res) => {
                this.thirdPids = res.data.result;
            });
        },
        productTypeHandleSizeChange(size) {
            this.productTypePageSize = size;
            this.tmallProductType();
        },
        productTypeHandleCurrentChange(index) {
            this.productTypeCurrentPage = index;
            this.tmallProductType();
        },
        tmallProductType() {
            axios({
                url: "/cloudToCloud/api/web/productType/list",
                params: {
                    index: this.productTypeCurrentPage,
                    size: this.productTypePageSize,
                    thirdPartyCloud: "TMALL",
                    productId: this.queryFrom.productId
                },
            }).then((res) => {
                var result = res.data.result;
                this.productTypeTotal = result.total;
                this.productTypeTable = result.records;
            });
        },
        productTypeEdit(productId) {
            axios({
                url: "/cloudToCloud/api/web/productType/detail",
                params: {
                    productId: productId,
                    thirdPartyCloud: "TMALL"
                }
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    var data = result.result;
                    this.productType.productId = data.productId;
                    this.productType.thirdPartyCloud = data.thirdPartyCloud;
                    this.productType.productTypes = data.productTypes;
                    this.isProductTypeUpdate = true;
                    this.productTypeDialog = true;
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productTypeSave() {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productType/save",
                data: {
                    productId: this.productType.productId,
                    thirdPartyCloud: "TMALL",
                    productTypes: this.productType.productTypes,
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productTypeDialog = false;
                    this.productType = this.$options.data().productType;
                    this.tmallProductType();
                    this.$message.success("保存成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productTypeDelete(productId) {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productType/delete",
                data: {
                    productId: productId,
                    thirdPartyCloud: "TMALL",
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.tmallProductType();
                    this.$message.success("删除成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productTypeCancel() {
            console.log("clear")
            this.productTypeDialog = false;
            this.productType = this.$options.data().productType;
            this.isProductTypeUpdate = false;
        },


        //属性映射方法
        functionMappingRemoveRow(mapping, index) {
            mapping.splice(index, 1);
        }
        ,
        functionMappingAddRow(index) {
            this.productFunction.productFunctions[index].valueMapping.push({
                key: "",
                value: ""
            })
        },
        productFunctionRemoveRow(index) {
            this.productFunction.productFunctions.splice(index, 1);
        },
        productFunctionAddRow() {
            this.productFunction.productFunctions.push({
                id: "",
                signCode: "",
                functionId: "",
                thirdSignCode: "",
                valueOf: false,
                valueMapping: [],
                convertFunction: "",
                capabilityConfigId: [],
                thirdActionCode: "",
                remark: ""
            });
        },
        productFunctionThirdIds() {
        },
        productFunctionHandleSizeChange(size) {
            this.productFunctionPageSize = size;
            this.tmallProductFunction();
        },
        productFunctionHandleCurrentChange(index) {
            this.productFunctionCurrentPage = index;
            this.tmallProductFunction();
        },
        tmallProductFunction() {
            axios({
                url: "/cloudToCloud/api/web/productFunction/list",
                params: {
                    index: this.productFunctionCurrentPage,
                    size: this.productFunctionPageSize,
                    thirdPartyCloud: "TMALL",
                    productId: this.queryFrom.productId
                },
            }).then((res) => {
                var result = res.data.result;
                this.productFunctionTotal = result.total;
                this.productFunctionTable = result.records;
            });
        },
        productFunctionEdit(productId) {
            axios({
                url: "/cloudToCloud/api/web/productFunction/detail",
                params: {
                    productId: productId,
                    thirdPartyCloud: "TMALL"
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    var data = result.result;
                    this.productFunction.productId = data.productId;
                    this.productFunction.thirdPartyCloud = data.thirdPartyCloud;
                    this.productFunction.productFunctions = data.productFunctions;
                    this.isProductFunctionUpdate = true;
                    this.productFunctionDialog = true;
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productFunctionSave() {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productFunction/save",
                data: {
                    productId: this.productFunction.productId,
                    thirdPartyCloud: "TMALL",
                    productFunctions: this.productFunction.productFunctions,
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productFunctionDialog = false;
                    this.productFunction = this.$options.data().productFunction;
                    this.tmallProductFunction();
                    this.$message.success("保存成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productFunctionDelete(productId) {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productFunction/delete",
                data: {
                    productId: productId,
                    thirdPartyCloud: "TMALL",
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.tmallProductFunction();
                    this.$message.success("删除成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productFunctionCancel() {
            console.log("clear")
            this.productFunctionDialog = false;
            this.productFunction = this.$options.data().productFunction;
            this.isProductFunctionUpdate = false;
        },

        //属性映射方法
        mappingRemoveRow(mapping, index) {
            mapping.splice(index, 1);
        }
        ,
        functionMappingAddRowEditPanel() {
            this.productFunctionEditPanelFrom.valueMapping.push({
                key: "",
                value: ""
            })
        },
        productFunctionEditRow(row, index) {
            this.productFunctionEditPanelFrom = row;
            this.productFunctionEditPanelFrom.index = index;
            this.productFunctionEditDialog = true;
        },
    },
});
