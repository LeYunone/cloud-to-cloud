Vue.component("google-config", {
    template: `
    <div>
        <div>
            <div style="text-align: center; font-weight: bold; position: relative; top: -5px;">< Google配置页面 ></div>
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
                    <el-button type="primary" @click="googleProductType">查询</el-button>
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
                <el-tab-pane name="productFunction" label="属性映射">
                    <el-form :inline="true"  class="demo-form-inline">
                      <el-form-item label="开发云产品id">
                        <el-input v-model="queryFrom.productId" placeholder="产品id"></el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="googleProductFunction">查询</el-button>
                        <el-button type="primary" @click="productFunctionDialog = true">新增</el-button>
                      </el-form-item>
                    </el-form>
                    <el-table :data="productFunctionTable" style="height:500px;overflow: auto;width: 100%">
                        <el-table-column prop="productId" label="开发云产品ID"></el-table-column>
                        <el-table-column prop="thirdProductIds" label="产商云产品ID">
                            <template slot-scope="scope">
                                <span v-html="scope.row.thirdProductIds.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="thirdSignCodes" label="产商云属性code">
                            <template slot-scope="scope">
                                <span v-html="scope.row.thirdSignCodes.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="thirdPartyCloud" label="产商云"></el-table-column>
                        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" @click="productFunctionEdit(scope.row.productId)">编辑</el-button>
                                <el-button type="text" @click="productFunctionDelete(scope.row.productId)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="productFunctionHandleSizeChange"
                            @current-change="productFunctionHandleCurrentChange"
                            :current-page="productFunctionCurrentPage"
                            :page-sizes="[10, 20, 30, 40]"
                            :page-size="productFunctionPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :productTypeTotal="productFunctionTotal"
                    >
                    </el-pagination>
                </el-tab-pane>
                <el-tab-pane name="productAction" label="行为映射">
                    <el-form :inline="true"  class="demo-form-inline">
                      <el-form-item label="开发云产品id">
                        <el-input v-model="queryFrom.productId" placeholder="产品id"></el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="googleProductAction">查询</el-button>
                    <el-button type="primary" @click="productActionDialog = true">新增</el-button>
                      </el-form-item>
                    </el-form>
                    <el-table :data="productActionTable" style="height:500px;overflow: auto;width: 100%">
                        <el-table-column prop="productId" label="开发云产品ID"></el-table-column>
                        <el-table-column prop="thirdProductIds" label="产商云产品ID">
                            <template slot-scope="scope">
                                <span v-html="scope.row.thirdProductIds.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="signCodes" label="开发云属性code">
                            <template slot-scope="scope">
                                <span v-html="scope.row.signCodes.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="thirdSignCodes" label="产商云属性">
                            <template slot-scope="scope">
                                <span v-html="scope.row.thirdSignCodes.join('<br>')"></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="thirdPartyCloud" label="产商云"></el-table-column>
                        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" @click="productActionEdit(scope.row.productId)">编辑</el-button>
                                <el-button type="text" @click="productActionDelete(scope.row.productId)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="productActionHandleSizeChange"
                            @current-change="productActionHandleCurrentChange"
                            :current-page="productActionCurrentPage"
                            :page-sizes="[10, 20, 30, 40]"
                            :page-size="productActionPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :productTypeTotal="productActionTotal"
                    >
                    </el-pagination>
                </el-tab-pane>
                <el-tab-pane name="productCapability" label="三方属性产商配置">
                    <el-button type="primary" @click="productCapabilityDialog = true">新增</el-button>
                    <el-table :data="productCapabilityTable" style="height:500px;overflow: auto;width: 100%">
                        <el-table-column prop="capabilityConfiguration" label="技能配置"></el-table-column>
                        <el-table-column prop="instanceName" label="字段名"></el-table-column>
                        <el-table-column prop="description" label="备注"></el-table-column>
                        <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" @click="productCapabilityEdit(scope.row.id)">编辑</el-button>
                                <el-button type="text" @click="productCapabilityDelete(scope.row.id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="productCapabilityHandleSizeChange"
                            @current-change="productCapabilityHandleCurrentChange"
                            :current-page="productCapabilityCurrentPage"
                            :page-sizes="[10, 20, 30, 40]"
                            :page-size="productCapabilityPageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :productTypeTotal="productCapabilityTotal"
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
                    <el-table-column label="第三方产品名" prop="thirdProduct">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.thirdProduct"></el-input>
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

        <!--属性映射表-->
        <el-dialog title="产品属性表单" :visible.sync="productFunctionDialog" width="80%"
                   :before-close="productFunctionCancel">
            <el-form ref="productFunction" :model="productFunction" label-width="100px">
                <el-form-item label="开发云产品id">
                    <el-input v-model="productFunction.productId" :disabled="isProductFunctionUpdate"></el-input>
                </el-form-item>

                <el-table :data="productFunction.productFunctions" style="width: 100%">
                    <el-table-column label="开发云属性" prop="signCode">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.signCode"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="开发云属性标识" prop="functionId">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.functionId"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="产商云属性" prop="thirdSignCode">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.thirdSignCode"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="产商云属性行为" prop="thirdActionCode">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.thirdActionCode"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="是否值映射" prop="valueOf">
                        <template slot-scope="scope">
                            <el-switch
                              v-model="scope.row.valueOf"
                              active-text="是"
                              inactive-text="否"
                              @change="changeStatus($event,scope.row)"
                              >
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="映射关系" prop="valueMapping" width="300">
                        <template slot-scope="scope">
                            <div v-for="(item, index) in scope.row.valueMapping">
                                <el-input v-model="item.key" style="width: 80px;margin-right: 5px;"></el-input>
                                <span>=</span>
                                <el-input v-model="item.value" style="width:80px;margin-right: 5px;"></el-input>
                                <el-button type="text" @click="functionMappingRemoveRow(scope.row.valueMapping, index)">
                                    删除
                                </el-button>
                            </div>
                            <el-button @click="functionMappingAddRow(scope.$index)">新增</el-button>
                        </template>
                    </el-table-column>

                    <el-table-column label="转换函数" prop="convertFunction">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.convertFunction" placeholder="NONE" clearable filterable>
                                <el-option
                                        v-for="item in convertFunctions"
                                        :key="item"
                                        :label="item"
                                        :value="item"
                                ></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="产商属性配置" prop="capabilityConfigIds">
                        <template slot-scope="scope">
                            <el-select multiple v-model="scope.row.capabilityConfigIds" placeholder="NONE" clearable filterable>
                                <el-option
                                        v-for="item in deviceCapabilities"
                                        :key="item.id"
                                        :label="item.description"
                                        :value="item.id+''"
                                ></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" prop="remark">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.remark"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="productFunctionEditRow(scope.row,scope.$index)">编辑</el-button>
                            <el-button type="text" @click="productFunctionRemoveRow(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <el-button @click="productFunctionAddRow">新增行</el-button>
            <span slot="footer" class="dialog-footer">
          <el-button @click="productFunctionCancel">取消</el-button>
          <el-button type="primary" @click="productFunctionSave">保存</el-button>
        </span>
        </el-dialog>
        <!--属性映射表 编辑面板-->
        <el-dialog title="编辑面板" :visible.sync="productFunctionEditDialog" width="60%">
            <el-form ref="productFunctionEditPanelFrom" :model="productFunctionEditPanelFrom" label-width="120px">
              <el-form-item label="开发云属性">
                <el-input v-model="productFunctionEditPanelFrom.signCode"></el-input>
              </el-form-item>
              <el-form-item label="开发云属性标识">
                <el-input v-model="productFunctionEditPanelFrom.functionId"></el-input>
              </el-form-item>
              <el-form-item >
                <template #label>
                   <el-tooltip class="item" effect="dark" content='控制协议产商云请求入参的解析结构，比如：{"brightness": 65} 说明brightness为控制指令的key，
  {"color":{"spectrumRGB":16711935}} 说明取color中的spectrumRGB'>
                              <i class="el-icon-question">产商云属性</i>
                            </el-tooltip>
                </template>
                <el-input type="textarea"  @input="formatJson(productFunctionEditPanelFrom.thirdSignCode)" :autosize="{ minRows: 2, maxRows: 10}" v-model="productFunctionEditPanelFrom.thirdSignCode"></el-input>
              </el-form-item>
              <el-form-item label="产商云属性行为">
                 <el-input v-model="productFunctionEditPanelFrom.thirdActionCode"></el-input>
              </el-form-item>
              <el-form-item label="是否值映射">
                <el-switch
                  v-model="productFunctionEditPanelFrom.valueOf"
                  active-text="是"
                  inactive-text="否"
                  @change="changeStatus($event,productFunctionEditPanelFrom)"
                  >
                </el-switch>
              </el-form-item>
              <el-form-item label="映射关系">
                <div v-for="(item, index) in productFunctionEditPanelFrom.valueMapping">
                    <el-input v-model="item.key" style="width: 150px;margin-right: 5px;"></el-input>
                    <span>=</span>
                    <el-input v-model="item.value" style="width:150px;margin-right: 5px;"></el-input>
                    <el-button type="text" @click="mappingRemoveRow(productFunctionEditPanelFrom.valueMapping, index)">
                        删除
                    </el-button>
                </div>
                <el-button @click="functionMappingAddRowEditPanel">新增</el-button>
              </el-form-item>
              <el-form-item label="转换函数">
                    <el-select v-model="productFunctionEditPanelFrom.convertFunction" placeholder="NONE" clearable filterable>
                        <el-option
                                v-for="item in convertFunctions"
                                :key="item"
                                :label="item"
                                :value="item"
                        ></el-option>
                    </el-select>
              </el-form-item>
               <el-form-item label="产商属性配置">
                      <el-select multiple v-model="productFunctionEditPanelFrom.capabilityConfigIds" placeholder="NONE" clearable filterable>
                                <el-option
                                        v-for="item in deviceCapabilities"
                                        :key="item.id"
                                        :label="item.description"
                                        :value="item.id+''"
                                ></el-option>
                      </el-select>
                </el-form-item>
              <el-form-item label="备注">
                <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}"  v-model="productFunctionEditPanelFrom.remark"></el-input>
              </el-form-item>
            </el-form>
        </el-dialog>

        <!--控制映射表-->
        <el-dialog title="产品行为表单" :visible.sync="productActionDialog" width="80%"
                   :before-close="productActionCancel">
            <el-form ref="productAction" :model="productAction" label-width="100px">
                <el-form-item label="开发云产品id">
                    <el-input v-model="productAction.productId" :disabled="isProductActionUpdate"></el-input>
                </el-form-item>
                <el-table :data="productAction.productActions" style="width: 100%">
                    <el-table-column label="开发云属性" prop="signCode">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.signCode"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="开发云属性标识" prop="functionId">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.functionId"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="thirdSignCode">
                        <template slot="header" slot-scope="scope">
                            <el-tooltip class="item" effect="dark" content='控制协议产商云请求入参的解析结构，比如：{"brightness": 65} 说明brightness为控制指令的key，\n  {"color":{"spectrumRGB":16711935}} 说明取color中的spectrumRGB'>
                              <i class="el-icon-question">产商云属性</i>
                            </el-tooltip>
                        </template>
                        
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.thirdSignCode" :rows="2" type="textarea" placeholder="支持 JSON 字符串" @blur="formatJson(scope.row)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="产商云属性行为" prop="thirdActionCode">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.thirdActionCode"></el-input>
                        </template>
                    </el-table-column>
                    
                    <el-table-column label="是否值映射" prop="valueOf">
                        <template slot-scope="scope">
                            <el-switch
                              v-model="scope.row.valueOf"
                               active-text="是"
                               inactive-text="否"
                               @change="changeStatus($event,scope.row)"
                             >
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column label="映射关系" prop="valueMapping" width="300">
                        <template slot-scope="scope">
                            <div v-for="(item, index) in scope.row.valueMapping">
                                <el-input v-model="item.key" style="width: 80px;margin-right: 5px;"></el-input>
                                <span>=</span>
                                <el-input v-model="item.value" style="width:80px;margin-right: 5px;"></el-input>
                                <el-button type="text" @click="mappingRemoveRow(scope.row.valueMapping, index)">
                                    删除
                                </el-button>
                            </div>
                            <el-button @click="actionMappingAddRow(scope.$index)">新增</el-button>
                        </template>
                    </el-table-column>

                    <el-table-column label="转换函数" prop="convertFunction">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.convertFunction" placeholder="NONE" clearable filterable>
                                <el-option
                                        v-for="item in convertFunctions"
                                        :key="item"
                                        :label="item"
                                        :value="item"
                                ></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" prop="remark">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.remark"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="productActionEditRow(scope.row,scope.$index)">编辑</el-button>
                            <el-button type="text" @click="productActionRemoveRow(scope.$index)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form>
            <el-button @click="productActionAddRow">新增行</el-button>
            <span slot="footer" class="dialog-footer">
          <el-button @click="productActionCancel">取消</el-button>
          <el-button type="primary" @click="productActionSave">保存</el-button>
        </span>
        </el-dialog>
        
        <!--控制映射表 编辑面板-->
        <el-dialog title="编辑面板" :visible.sync="productActionEditDialog" width="60%">
            <el-form ref="productActionEditPanelFrom" :model="productActionEditPanelFrom" label-width="120px">
              <el-form-item label="开发云属性">
                <el-input v-model="productActionEditPanelFrom.signCode"></el-input>
              </el-form-item>
              <el-form-item label="开发云属性标识">
                <el-input v-model="productActionEditPanelFrom.functionId"></el-input>
              </el-form-item>
              <el-form-item >
                <template #label>
                   <el-tooltip class="item" effect="dark" content='控制协议产商云请求入参的解析结构，比如：{"brightness": 65} 说明brightness为控制指令的key，\n  {"color":{"spectrumRGB":16711935}} 说明取color中的spectrumRGB'>
                              <i class="el-icon-question">产商云属性</i>
                            </el-tooltip>
                </template>
                <el-input type="textarea"  @blur="formatJson(productActionEditPanelFrom.thirdSignCode)" :autosize="{ minRows: 2, maxRows: 10}" v-model="productActionEditPanelFrom.thirdSignCode"></el-input>
              </el-form-item>
              <el-form-item label="产商云属性行为">
                 <el-input v-model="productActionEditPanelFrom.thirdActionCode"></el-input>
              </el-form-item>
              <el-form-item label="是否值映射">
                <el-switch
                  v-model="productActionEditPanelFrom.valueOf"
                  active-text="是"
                  inactive-text="否"
                  @change="changeStatus($event,productActionEditPanelFrom)"
                  >
                </el-switch>
              </el-form-item>
              <el-form-item label="映射关系">
                <div v-for="(item, index) in productActionEditPanelFrom.valueMapping">
                    <el-input v-model="item.key" style="width: 150px;margin-right: 5px;"></el-input>
                    <span>=</span>
                    <el-input v-model="item.value" style="width:150px;margin-right: 5px;"></el-input>
                    <el-button type="text" @click="mappingRemoveRow(productActionEditPanelFrom.valueMapping, index)">
                        删除
                    </el-button>
                </div>
                <el-button @click="actionMappingAddRowEditPanel">新增</el-button>
              </el-form-item>
              <el-form-item label="转换函数">
                    <el-select v-model="productActionEditPanelFrom.convertFunction" placeholder="NONE" clearable filterable>
                        <el-option
                                v-for="item in convertFunctions"
                                :key="item"
                                :label="item"
                                :value="item"
                        ></el-option>
                    </el-select>
              </el-form-item>
              <el-form-item label="备注">
                <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}"  v-model="productActionEditPanelFrom.remark"></el-input>
              </el-form-item>
            </el-form>
        </el-dialog>
        
        <!--产商属性配置表-->
        <el-dialog title="产商属性配置表" :visible.sync="productCapabilityDialog" width="60%"
           :before-close="productCapabilityCancel">
            <el-form ref="productCapability" :model="productCapability" label-width="120px">
                <el-form-item label="技能配置">
                        <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}"  v-model="productCapability.capabilityConfiguration"></el-input>
                </el-form-item>
                <el-form-item label="字段名">
                        <el-input v-model="productCapability.instanceName"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                        <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 5}"  v-model="productCapability.description"></el-input>
                </el-form-item>
            </el-form>
            
            <span slot="footer" class="dialog-footer">
                  <el-button @click="productCapabilityCancel">取消</el-button>
                  <el-button type="primary" @click="productCapabilitySave">保存</el-button>
            </span>
        </el-dialog>
        
    </div>
  `,
    mounted: function () {
        this.googleProductType();
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
                cloud: "GOOGLE"
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
                thirdPartyCloud: "GOOGLE",
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
                thirdPartyCloud: "GOOGLE",
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
                thirdPartyCloud: "GOOGLE",
                productFunctions: [
                    {
                        id: "",
                        signCode: "",
                        functionId: "",
                        thirdSignCode: "",
                        valueOf: false,
                        valueMapping: [],
                        convertFunction: "",
                        capabilityConfigIds: [],
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
                thirdPartyCloud: "GOOGLE",
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

            isProductCapabilityUpdate: false,
            productCapabilityTable: [],
            productCapabilityCurrentPage: 1, // 当前页数
            productCapabilityPageSize: 10, // 每页显示条数
            productCapabilityTotal: 0, // 总条数
            productCapabilityDialog: false, //产品映射弹窗
            productCapability: {
                thirdPartyCloud: "GOOGLE",
                id: "",
                capabilitySemantics: "",
                capabilityConfiguration: "",
                instanceName: "",
                valueSemantics: "",
                description: ""
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
                capabilityConfigIds: [],
                remark: ""
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
                this.googleProductType();
            } else if (this.panelActiveName === "productFunction") {
                this.googleProductFunction();
            } else if (this.panelActiveName === "productAction") {
                this.googleProductAction();
            } else if (this.panelActiveName === "productCapability") {
                this.googleProductCapability();
            }
        },
        clientConfigDetail() {
            axios({
                url: "/cloudToCloud/api/web/config/clientConfig",
                params: {
                    "cloud": "GOOGLE"
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
                    thirdPartyCloud: "GOOGLE",
                },
            }).then((res) => {
                this.thirdPids = res.data.result;
            });
        },
        productTypeHandleSizeChange(size) {
            this.productTypePageSize = size;
            this.googleProductType();
        },
        productTypeHandleCurrentChange(index) {
            this.productTypeCurrentPage = index;
            this.googleProductType();
        },
        googleProductType() {
            axios({
                url: "/cloudToCloud/api/web/productType/list",
                params: {
                    index: this.productTypeCurrentPage,
                    size: this.productTypePageSize,
                    thirdPartyCloud: "GOOGLE",
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
                    thirdPartyCloud: "GOOGLE"
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
                    thirdPartyCloud: "GOOGLE",
                    productTypes: this.productType.productTypes,
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productTypeDialog = false;
                    this.productType = this.$options.data().productType;
                    this.googleProductType();
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
                    thirdPartyCloud: "GOOGLE",
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.googleProductType();
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
                capabilityConfigIds: [],
                thirdActionCode: "",
                remark: ""
            });
        },
        productFunctionThirdIds() {
        },
        productFunctionHandleSizeChange(size) {
            this.productFunctionPageSize = size;
            this.googleProductFunction();
        },
        productFunctionHandleCurrentChange(index) {
            this.productFunctionCurrentPage = index;
            this.googleProductFunction();
        },
        googleProductFunction() {
            axios({
                url: "/cloudToCloud/api/web/productFunction/list",
                params: {
                    index: this.productFunctionCurrentPage,
                    size: this.productFunctionPageSize,
                    thirdPartyCloud: "GOOGLE",
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
                    thirdPartyCloud: "GOOGLE"
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
                    thirdPartyCloud: "GOOGLE",
                    productFunctions: this.productFunction.productFunctions.map(item =>{
                        const { capabilityConfigId, ...rest } = item;
                        return rest;
                    }),
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productFunctionDialog = false;
                    this.productFunction = this.$options.data().productFunction;
                    this.googleProductFunction();
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
                    thirdPartyCloud: "GOOGLE",
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.googleProductFunction();
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
        actionMappingAddRow(index) {
            this.productAction.productActions[index].valueMapping.push({
                key: "",
                value: ""
            })
        },
        actionMappingAddRowEditPanel() {
            this.productActionEditPanelFrom.valueMapping.push({
                key: "",
                value: ""
            })
        },
        functionMappingAddRowEditPanel() {
            this.productFunctionEditPanelFrom.valueMapping.push({
                key: "",
                value: ""
            })
        },
        productActionRemoveRow(index) {
            this.productAction.productActions.splice(index, 1);
        },
        productActionEditRow(row, index) {
            this.productActionEditPanelFrom = row;
            this.productActionEditPanelFrom.index = index;
            this.productActionEditDialog = true;
        },
        productFunctionEditRow(row, index) {
            this.productFunctionEditPanelFrom = row;
            this.productFunctionEditPanelFrom.index = index;
            this.productFunctionEditDialog = true;
        },
        productActionAddRow() {
            this.productAction.productActions.push({
                id: "",
                signCode: "",
                functionId: "",
                thirdSignCode: "",
                valueOf: false,
                valueMapping: [],
                thirdActionCode: "",
                convertFunction: "",
                remark: ""
            });
        },
        productActionThirdIds() {
        },
        productActionHandleSizeChange(size) {
            this.productActionPageSize = size;
            this.googleProductAction();
        },
        productActionHandleCurrentChange(index) {
            this.productActionCurrentPage = index;
            this.googleProductAction();
        },
        googleProductAction() {
            axios({
                url: "/cloudToCloud/api/web/action/list",
                params: {
                    index: this.productActionCurrentPage,
                    size: this.productActionPageSize,
                    thirdPartyCloud: "GOOGLE",
                    productId: this.queryFrom.productId
                },
            }).then((res) => {
                var result = res.data.result;
                this.productActionTotal = result.total;
                this.productActionTable = result.records;
            });
        },
        productActionEdit(productId) {
            axios({
                url: "/cloudToCloud/api/web/action/detail",
                params: {
                    productId: productId,
                    thirdPartyCloud: "GOOGLE"
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    var data = result.result;
                    this.productAction.productId = data.productId;
                    this.productAction.thirdPartyCloud = data.thirdPartyCloud;
                    this.productAction.productActions = data.productActions;
                    this.isProductActionUpdate = true;
                    this.productActionDialog = true;
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productActionSave() {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/action/save",
                data: {
                    productId: this.productAction.productId,
                    thirdPartyCloud: "GOOGLE",
                    productActions: this.productAction.productActions,
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productActionDialog = false;
                    this.productAction = this.$options.data().productAction;
                    this.googleProductAction();
                    this.$message.success("保存成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productActionDelete(productId) {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/action/delete",
                data: {
                    productId: productId,
                    thirdPartyCloud: "GOOGLE",
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.googleProductAction();
                    this.$message.success("删除成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productActionCancel() {
            console.log("clear")
            this.productActionDialog = false;
            this.productAction = this.$options.data().productAction;
            this.isProductFunctionUpdate = false;
        },

        //属性配置
        productCapabilityHandleSizeChange(size) {
            this.productCapabilityPageSize = size;
            this.googleProductCapability();
        },
        productCapabilityHandleCurrentChange(index) {
            this.productCapabilityCurrentPage = index;
            this.googleProductCapability();
        },
        googleProductCapability() {
            axios({
                url: "/cloudToCloud/api/web/productCapability/list",
                params: {
                    index: this.productCapabilityCurrentPage,
                    size: this.productCapabilityPageSize,
                    thirdPartyCloud: "GOOGLE",
                },
            }).then((res) => {
                var result = res.data.result;
                this.productCapabilityTotal = result.total;
                this.productCapabilityTable = result.records;
            });
        },
        productCapabilityEdit(id) {
            axios({
                url: "/cloudToCloud/api/web/productCapability/detail",
                params: {
                    id: id,
                    thirdPartyCloud: "GOOGLE"
                },
            }).then((res) => {
                var result = res.data.result;
                this.productCapability.productId = result.productId;
                this.productCapability.thirdPartyCloud = result.thirdPartyCloud;
                this.productCapability.productCapabilitys = result.productCapabilitys;
                this.isProductCapabilityUpdate = true;
                this.productCapabilityDialog = true;
            });
        },
        productCapabilitySave() {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productCapability/save",
                data: this.productCapability,
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.productCapabilityDialog = false;
                    this.productCapability = this.$options.data().productCapability;
                    this.googleProductCapability();
                    this.$message.success("保存成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productCapabilityDelete(id) {
            axios({
                method: "POST",
                url: "/cloudToCloud/api/web/productCapability/delete",
                data: {
                    id: id
                },
            }).then((res) => {
                var result = res.data;
                if (result.success) {
                    this.googleProductCapability();
                    this.$message.success("删除成功");
                } else {
                    this.$message.error(result.message);
                }
            });
        },
        productCapabilityCancel() {
            console.log("clear")
            this.productCapabilityDialog = false;
            this.productCapability = this.$options.data().productCapability;
            this.isProductCapabilityUpdate = false;
        }
    },
});
