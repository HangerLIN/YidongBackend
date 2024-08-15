<template>
	<xn-form-container
		:title="formData.id ? '编辑项目基础信息' : '增加项目基础信息'"
		:width="700"
		v-model:open="open"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="名称：" name="name">
				<a-input v-model:value="formData.name" placeholder="请输入名称" allow-clear />
			</a-form-item>
			<a-form-item label="公司名称：" name="companyName">
				<a-input v-model:value="formData.companyName" placeholder="请输入公司名称" allow-clear />
			</a-form-item>
			<a-form-item label="下属：" name="subordinate">
				<a-input v-model:value="formData.subordinate" placeholder="请输入下属" allow-clear />
			</a-form-item>
			<a-form-item label="电话：" name="phone">
				<a-input v-model:value="formData.phone" placeholder="请输入电话" allow-clear />
			</a-form-item>
			<a-form-item label="项目经理：" name="projectManager">
				<a-input v-model:value="formData.projectManager" placeholder="请输入项目经理" allow-clear />
			</a-form-item>
			<a-form-item label="项目经理电话：" name="projectManagerPhone">
				<a-input v-model:value="formData.projectManagerPhone" placeholder="请输入项目经理电话" allow-clear />
			</a-form-item>
			<a-form-item label="项目经理单位：" name="projectManagerUnit">
				<a-input v-model:value="formData.projectManagerUnit" placeholder="请输入项目经理单位" allow-clear />
			</a-form-item>
			<a-form-item label="项目类型1：" name="projectType1">
				<a-input v-model:value="formData.projectType1" placeholder="请输入项目类型1" allow-clear />
			</a-form-item>
			<a-form-item label="项目类型2：" name="projectType2">
				<a-input v-model:value="formData.projectType2" placeholder="请输入项目类型2" allow-clear />
			</a-form-item>
			<a-form-item label="项目名称：" name="projectName">
				<a-input v-model:value="formData.projectName" placeholder="请输入项目名称" allow-clear />
			</a-form-item>
			<a-form-item label="项目编码：" name="projectCode">
				<a-input v-model:value="formData.projectCode" placeholder="请输入项目编码" allow-clear />
			</a-form-item>
			<a-form-item label="评估周期：" name="evaluationPeriod">
				<a-input v-model:value="formData.evaluationPeriod" placeholder="请输入评估周期" allow-clear />
			</a-form-item>
			<a-form-item label="资源周期：" name="resourcePeriod">
				<a-input v-model:value="formData.resourcePeriod" placeholder="请输入资源周期" allow-clear />
			</a-form-item>
			<a-form-item label="建设周期：" name="buildPeriod">
				<a-input v-model:value="formData.buildPeriod" placeholder="请输入建设周期" allow-clear />
			</a-form-item>
			<a-form-item label="评估时间：" name="evaluationTime">
				<a-input v-model:value="formData.evaluationTime" placeholder="请输入评估时间" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button type="primary" @click="nextTouzi" :loading="submitLoading">下一步</a-button>
		</template>
	</xn-form-container>
	<xn-form-container
		title="投资明细"
		:width="700"
		v-model:open="open1"
		:destroy-on-close="true"
		@close="onClose"
	>
<!--		<a-form ref="TouziformRef" :model="formData" :rules="formRules" layout="vertical">-->
<!--			<p style="font-size: large">基础信息：</p>-->
<!--			<a-form-item label="" name="name" rules="[{ required: true, message: '请输入或选择名称!' }]"  :wrapper-col="{ span: 16 }">-->
<!--				<a-row>-->
<!--					<a-col :span="12">-->
<!--						<a-select v-model:value="BasicInformation.key1" placeholder="请选择名称" allow-clear  style="width: 100%;">-->
<!--							<a-select-option v-for="item in calendarTypeOptions" :key="item.value" :value="item.value">-->
<!--								{{ item.label }}-->
<!--							</a-select-option>-->
<!--						</a-select>-->
<!--					</a-col>-->
<!--					<a-col :span="12">-->
<!--						<a-input v-model:value="BasicInformation.value1" :placeholder="`请输入${BasicInformation.key1}`" allow-clear style="width: 100%;" />-->
<!--					</a-col>-->
<!--				</a-row>-->
<!--			</a-form-item>-->
<!--			<a-form-item label="" name="name" rules="[{ required: true, message: '请输入或选择名称!' }]"  :wrapper-col="{ span: 16 }">-->
<!--				<a-row>-->
<!--					<a-col :span="12">-->
<!--						<a-select v-model:value="BasicInformation.key2" placeholder="请选择名称" allow-clear style="width: 100%;">-->
<!--							<a-select-option v-for="item in calendarTypeOptions" :key="item.value" :value="item.value">-->
<!--								{{ item.label }}-->
<!--							</a-select-option>-->
<!--						</a-select>-->
<!--					</a-col>-->
<!--					<a-col :span="12">-->
<!--						<a-input v-model:value="BasicInformation.value2" :placeholder="`请输入${BasicInformation.key2}`" allow-clear style="width: 100%;" />-->
<!--					</a-col>-->
<!--				</a-row>-->
<!--			</a-form-item>-->
<!--			<a-form-item label="" name="name" rules="[{ required: true, message: '请输入或选择名称!' }]"  :wrapper-col="{ span: 16 }">-->
<!--				<a-row>-->
<!--					<a-col :span="12">-->
<!--						<a-select v-model:value="BasicInformation.key3" placeholder="请选择名称" allow-clear style="width: 100%;">-->
<!--							<a-select-option v-for="item in calendarTypeOptions" :key="item.value" :value="item.value">-->
<!--								{{ item.label }}-->
<!--							</a-select-option>-->
<!--						</a-select>-->
<!--					</a-col>-->
<!--					<a-col :span="12">-->
<!--						<a-input v-model:value="BasicInformation.value3" :placeholder="`请输入${BasicInformation.key3}`" allow-clear style="width: 100%;" />-->
<!--					</a-col>-->
<!--				</a-row>-->
<!--			</a-form-item>-->
<!--		</a-form>-->
<!--		<el-input v-model="input" placeholder="请输入内容"></el-input>-->
		<div>
			<touzi :cycle="cyc" @totalevent="handleDataUpdate" @ScheduleAndCosts="handleDataUpdate1"></touzi>
		</div>
		<div>
			<touzi-money :cycle="cyc" style="margin-top: 30px"></touzi-money>
		</div>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button type="primary" @click="nextshoru" :loading="submitLoading">下一步</a-button>
		</template>
	</xn-form-container>
	<xn-form-container
		title="收入明细"
		:width="700"
		v-model:open="open2"
		:destroy-on-close="true"
		@close="onClose"
	>
		<div>
			<shouru :shouru_cycle="shouru_cycle" @totalevent="handleDataUpdate" @ScheduleAndCosts="handleDataUpdate1"></shouru>
		</div>
<!--		<div>-->
<!--			<touzi-money :cycle="cyc" style="margin-top: 30px"></touzi-money>-->
<!--		</div>-->


		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button type="primary" @click="nextMingxi" :loading="submitLoading">下一步</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="projectBasicInfoForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import projectBasicInfoApi from '@/api/biz/projectBasicInfoApi'
	import Touzi from "@/views/biz/basicinfo/touzi.vue";
	import TouziMoney from "@/views/biz/basicinfo/touziMoney.vue";
	import Shouru from "@/views/biz/basicinfo/shouru.vue";

	const totaluniclude = ref()
	const scheduleAndCosts = ref()
	const handleDataUpdate=(data)=>{
		console.log(11111111111111)
		totaluniclude.value = data
		console.log(totaluniclude); // 输出：Hello from Child
	}
	const handleDataUpdate1=(data)=>{
		console.log(22222222222222)
		scheduleAndCosts.value=data
		console.log(scheduleAndCosts); // 输出：Hello from Child
	}

	provide('sharedData', totaluniclude);
	provide('sharedData1', scheduleAndCosts);
	// 抽屉状态
	let cyc=ref(0)
	let shouru_cycle=ref(0)
	const open = ref(false)
	const open1 = ref(false)
	const open2 = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	const TouziformRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const calendarTypeOptions = [
		{ label: '产品名称', value: '产品名称' },
		{ label: '产品代码', value: '产品代码' },
		{ label: '规格型号', value: '规格型号' },
	]
	//请求参数ref({})
	const listQuery=ref({
		projectId: '',
		//子项目基础信息
		basicInformation: [],
		//建设进度，条/--年
		subprojectSchedule: [],
		//投资金额，元/条--年
		subprojectSinglecost: [],
		//单挑造价，元/条--年
		subprojectSingleprice: [],
	},)
	//子项目基础信息
	const BasicInformation=ref({
		// key: '123',
		// value: ''
		})

	//建设进度，条/--年
	const SubprojectSchedule=ref({
		key: '',
		value: ''
	})

	//投资金额，元/条--年
	const SubprojectSinglecost=ref({
		key: '',
		value: ''
	})

	//建设进度，条/--年
	const SubprojectSingleprice=ref({
		key: '',
		value: ''
	})


	// 打开抽屉
	const onOpen = (record) => {
		console.log(record)
		open.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
			console.log(formData)
		}
	}
	const onOpen1 = (record) => {
		open1.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
	}
	const onOpen2 = (record) => {
		open2.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		// formRef.value.resetFields()
		// TouziformRef.value.resetFields()
		formData.value = {}
		open.value = false
		open1.value = false
	}
	// 默认要校验的
	const formRules = {
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value
			.validate()
			.then(() => {
				submitLoading.value = true
				const formDataParam = cloneDeep(formData.value)
				projectBasicInfoApi
					.projectBasicInfoSubmitForm(formDataParam, formDataParam.id)
					.then(() => {
						onClose()
						emit('successful')
					})
					.finally(() => {
						submitLoading.value = false
					})
			})
			.catch(() => {})
	}
	// 抛出函数
	defineExpose({
		onOpen,
		onOpen1,
		onOpen2

	})



	//投资明细
	const nextTouzi = () => {
		console.log(formData.value.buildPeriod)
		cyc.value=formData.value.buildPeriod
		shouru_cycle.value=formData.value.evaluationPeriod
		console.log('next'+cyc.value)
		onClose()
		console.log('next')
		onOpen1()
		console.log('next')
	}
	//nextMingxi
	const nextshoru = () => {
		// onClose()
		// console.log('next')
		// onOpen1()
		console.log('next'+cyc.value)
		onClose()
		console.log('next')
		onOpen2()
		console.log('next')
		// console.log(questionChoiceVOlist.value)
		// TouziformRef.value
		// 	.validate()
		// 	.then(() => {
		// 		/*下一步是否存储信息*/
		// 		// submitLoading.value = true
		// 		// const formDataParam = cloneDeep(formData.value)
		// 		// projectBasicInfoApi
		// 		// 	.projectBasicInfoSubmitForm(formDataParam, formDataParam.id)
		// 		// 	.then(() => {
		// 		// 		onClose()
		// 		// 		emit('successful')
		// 		// 	})
		// 		// 	.finally(() => {
		// 		// 		submitLoading.value = false
		// 		// 	})
		// 		onClose()
		// 		console.log('next')
		// 		onOpen1()
		// 		console.log('next')
		// 	})
		// 	.catch(() => {})
	}
</script>
