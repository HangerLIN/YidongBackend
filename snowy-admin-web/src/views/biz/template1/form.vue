<template>
	<xn-form-container
		:title="formData.transactionId ? '编辑template1' : '增加template1'"
		:width="700"
		v-model:open="open"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-row :gutter="16">
				<a-col :span="12">
					<a-form-item label="业务名称：" name="transactionName">
						<a-input v-model:value="formData.transactionName" placeholder="请输入业务名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="业务类型：" name="transactionType">
						<a-input v-model:value="formData.transactionType" placeholder="请输入业务类型" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="模版数量：" name="templateNum">
						<a-input v-model:value="formData.templateNum" placeholder="请输入模版数量" allow-clear />
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button style="margin-right: 8px" type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button v-if="formData.transactionId" type="primary" @click="nextshoru" :loading="submitLoading">子模块</a-button>
		</template>
	</xn-form-container>
	<xn-form-container
		:title="formData.transactionId ? '编辑Template1' : '增加Template1'"
		:width="1000"
		v-model:open="open1"
		:destroy-on-close="true"
		@close="onClose"
	>
		<ComponentA :TemplateId="formData1ID"></ComponentA>
		<!--		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">-->
		<!--			<a-row :gutter="16">-->
		<!--				<a-col :span="12">-->
		<!--					<a-form-item label="业务ID：" name="transactionId">-->
		<!--						<a-input v-model:value="formData.transactionId" placeholder="请输入业务ID" allow-clear />-->
		<!--					</a-form-item>-->
		<!--				</a-col>-->
		<!--				<a-col :span="12">-->
		<!--					<a-form-item label="模版名称：" name="templateName">-->
		<!--						<a-input v-model:value="formData.templateName" placeholder="请输入模版名称" allow-clear />-->
		<!--					</a-form-item>-->
		<!--				</a-col>-->
		<!--				<a-col :span="12">-->
		<!--					<a-form-item label="模版序号：" name="templateSerial">-->
		<!--						<a-input v-model:value="formData.templateSerial" placeholder="请输入模版序号" allow-clear />-->
		<!--					</a-form-item>-->
		<!--				</a-col>-->
		<!--				<a-col :span="12">-->
		<!--					<a-form-item label="子模版数量：" name="subtemplateNum">-->
		<!--						<a-input v-model:value="formData.subtemplateNum" placeholder="请输入子模版数量" allow-clear />-->
		<!--					</a-form-item>-->
		<!--				</a-col>-->
		<!--			</a-row>-->
		<!--		</a-form>-->

		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
<!--			<a-button style="margin-right: 8px" type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>-->
<!--			<a-button type="primary"  @click="nextshoru" :loading="submitLoading">下一步</a-button>-->
		</template>
	</xn-form-container>
</template>

<script setup name="template1Form">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import template1Api from '@/api/biz/template1Api'
	import ComponentA from '../Template2/index.vue';
	// 抽屉状态
	const open = ref(false)
	const open1 = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const formData1 = ref()
	const submitLoading = ref(false)
	let formData1ID = ref()

	// 打开抽屉
	const onOpen = (record) => {
		open.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
			formData1.value = recordData
		}
	}
	const onOpen1 = (record) => {
		open1.value = true
		console.log(formData1.value.transactionId)
		formData1ID = formData1.value.transactionId
		console.log(formData1ID)
		if (record) {
			let recordData = cloneDeep(record)
			console.log(111111)
			formData.value = Object.assign({}, recordData)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		// formRef.value.resetFields()
		formData.value = {}
		// open.value = false
	}
	const nextshoru = () => {
		// onClose()
		// console.log('next')
		// onOpen1()
		console.log('next')
		onClose()
		console.log('next')
		onOpen1()
		console.log('next')
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
				template1Api
					.template1SubmitForm(formDataParam, formDataParam.transactionId)
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
		onOpen
	})
</script>
