<template>
    <div>
        <el-row type="flex">
            <el-col>
                <el-steps :active="active" finish-status="success" align-center>
                <el-step title="写信" icon="el-icon-edit"></el-step>
                <el-step title="信息" icon="el-icon-info"></el-step>
                <el-step title="类型" icon="el-icon-picture"></el-step>
                <el-step title="上传" icon="el-icon-upload"></el-step>
                </el-steps>
            </el-col>
        </el-row>
        <el-row type="flex">
            <el-col>
                <el-form :model="form" :rules="rules" :ref="'letterForm'">
                    <div v-if="active===0">
                        <el-form-item prop="context">
                            <el-input style="font-size: large;" type="textarea" rows="20" placeholder="请输入内容" v-model="form.context" maxlength="500" show-word-limit></el-input>
                        </el-form-item>
                    </div>
                    <div v-else-if="active===1">
                        <el-form-item prop="receiver" label="收件方">
                            <el-input v-model="form.receiver" style="margin: 20px 20px 20px auto;"></el-input>
                        </el-form-item>
                        <el-form-item prop="receiverPhone" label="收件电话">
                            <el-input v-model="form.receiverPhone" style="margin: 20px 20px 20px auto;"></el-input>
                        </el-form-item>
                        <el-form-item prop="toAddress" label="目的地址">
                            <el-input v-model="form.toAddress" style="margin: 20px 20px 20px auto;"></el-input>
                        </el-form-item>
                        <el-form-item prop="postalCode" label="邮编">
                            <el-input v-model="form.postalCode" style="margin: 20px 20px 20px auto;"></el-input>
                        </el-form-item>
                    </div>
                    <div v-else-if="active===2">
                        <el-form-item prop="paperId" label="信纸类型">
                            <el-select v-model="form.paperId" placeholder="请选择信纸类型">
                                <el-option v-for="item in paperData" :key="item.id" :label="item.detail" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item prop="coverId" label="信封类型">
                            <el-select v-model="form.coverId" placeholder="请选择信封类型">
                                <el-option v-for="item in coverData" :key="item.id" :label="item.detail" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="语言">
                            <el-radio-group v-model="language">
                                <el-radio label="1">中文</el-radio>
                                <el-radio label="2">英文</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <div v-if="language === '1'">
                            <el-form-item prop="inkId" label="字体">
                                <el-select v-model="form.inkId" placeholder="请选择字体样式">
                                    <el-option  v-for="item in zhinkData" :key="item.id" :label="item.goodName" :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </div>
                        <div v-else>
                            <el-form-item prop="inkId" label="字体">
                                <el-select v-model="form.inkId" placeholder="请选择字体样式">
                                    <el-option  v-for="item in eninkData" :key="item.id" :label="item.goodName" :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                        </div>
                    </div>
                    <div v-else-if="active === 3">
                        <el-card class="box-card">
                            <el-descriptions title="信件" class="margin-top" :column="3" size="mini" border>
                            <el-descriptions-item label="用户名">{{ form.username }}</el-descriptions-item>
                            <el-descriptions-item label="联系方式">{{ form.callPhone }}</el-descriptions-item>
                            <el-descriptions-item label="收件方">{{ form.receiver }}</el-descriptions-item>
                            <el-descriptions-item label="收件电话">{{ form.receiverPhone }}</el-descriptions-item>
                            <el-descriptions-item label="源地址">{{ form.fromAddress }}</el-descriptions-item>
                            <el-descriptions-item label="目的地址">{{ form.toAddress }}</el-descriptions-item>
                            <el-descriptions-item label="邮编">{{ form.postalCode }}</el-descriptions-item>
                            </el-descriptions>
                            <el-divider>内容</el-divider>
                            <div v-if="form.inkId===13">
                                <span class="zh_CN_1">{{ form.context }}</span>
                            </div>
                            <div v-else-if="form.inkId===14">
                                <span class="zh_CN_2">{{ form.context }}</span>
                            </div>
                            <div v-else-if="form.inkId===15">
                                <span class="zh_CN_3">{{ form.context }}</span>
                            </div>
                            <div v-else-if="form.inkId===16">
                                <span class="en_US_4">{{ form.context }}</span>
                            </div>
                            <div v-else-if="form.inkId===17">
                                <span class="en_US_5">{{ form.context }}</span>
                            </div>
                            <div v-else-if="form.inkId===18">
                                <span class="en_US_6">{{ form.context }}</span>
                            </div>
                            <div v-else>
                                <span>{{ form.context }}</span>
                            </div>
                        </el-card>
                    </div>
                </el-form>
                <div>
                    <div v-if="active === 3">
                        <el-popconfirm title="确定重置吗？" @confirm="reset">
                            <el-button slot="reference" style="margin-top: 12px; margin-right: 12px;">重置</el-button>
                        </el-popconfirm>
                        <el-button style="margin-top: 12px;" @click="last">上一步</el-button>
                        <el-button style="margin-top: 12px;" @click="upload">完成</el-button>
                    </div>
                    <div v-else-if="active === 0">
                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                    <div v-else>
                        <el-button style="margin-top: 12px;" @click="last">上一步</el-button>
                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: "WritingView",
    data() {
        return {
            active: 0,
            form: {
                username: '',
                callPhone: '',
                fromAddress: '',
                paperId: 6,
                coverId: 12,
                status: 2
            },
            rules: {
                context: [
                    { required: true, message: '请输入内容', trigger: 'blur' },
                    { min: 10, max: 100, message: '长度在 10 到 100 个字符', trigger: 'blur' }
                ],
                receiver: [
                    { required: true, message: '请输入收件人信息', trigger: 'blur' },
                    { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
                ],
                receiverPhone: [
                    { required: true, message: '请输入收件电话', trigger: 'blur' },
                    { min: 11, max: 11, message: '11位电话号码', trigger: 'blur' }
                ],
                toAddress: [
                    { required: true, message: '请输入收件地址', trigger: 'blur' },
                    { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
                ],
                postalCode: [
                    { required: true, message: '请输入邮政编码', trigger: 'blur' },
                    { min: 6, max: 6, message: '6位邮政编码', trigger: 'blur' }
                ]
            },
            paperData: [],
            coverData: [],
            zhinkData: [],
            eninkData: [],
            language: '1',
        }
    },
    created() {
        this.getUser();
        this.load();
    },
    methods: {
        getUser() {
            this.axios.get("/user/local").then(res => {
                let result = res.data;
                if (result.code == 'C200') {
                    this.form.username = result.data.username;
                    this.form.callPhone = result.data.phone;
                    this.form.fromAddress = result.data.address;
                } else {
                    this.$message.error("请稍后重试");
                }
            }).catch(error => {
                this.$message.error(error.response.data.msg);
                this.$store.commit("logout");
            })
        },
        load() {
            this.axios.get("/good/all", {
                params: {
                    paper: 'paper',
                    cover: 'cover'
                }
            }).then(res => {
                let result = res.data;
                if (result.code === 'C200') {
                    this.paperData = result.data.paper;
                    this.coverData = result.data.cover;
                    this.zhinkData = result.data.zhink;
                    this.eninkData = result.data.enink;
                } else {
                    this.$message.error(result.msg);
                }
            }).catch(error => {
                this.$message.error(error.response.data.msg);
                this.$store.commit("logout");
            })
        },
        last() {
            if (this.active-- == 0) this.active = 0;
        },
        next() {
            this.$refs['letterForm'].validate(async (valid) => {
                if (valid) {
                    if (this.active++ > 2) this.active = 0;
                } else {
                    return false;
                }
            });
        },
        reset() {
            this.form = {
                username: '',
                callPhone: '',
                fromAddress: '',
                paperId: 6,
                coverId: 12,
                status: 2
            };
            this.next();
        },
        upload() {
            this.axios.post("/letter/upload", this.form).then(res => {
                let result = res.data;
                if (result.code == 'C200') {
                    this.$message.success("上传成功");
                    this.form = {};
                    this.next();
                } else {
                    this.$message.error(result.msg);
                }
            }).catch(error => {
                this.$message.error(error.response.data.msg);
                this.$store.commit("logout");
            })
        }
    }
}
</script>

<style>
@import "../../assets/font/font.css";

.zh_CN_1 {
  font-family: coki;
  font-size: 25px;
}
.zh_CN_2 {
  font-family: WCF;
  font-size: 25px;
}

.zh_CN_3 {
  font-family: WF;
  font-size: 25px;
}

.en_US_4 {
  font-family: willion;
  font-size: 25px;
}

.en_US_5 {
  font-family: family;
  font-size: 25px;
}

.en_US_6 {
  font-family: romeobohemian;
  font-size: 25px;
}
</style>