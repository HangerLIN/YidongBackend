<template>
	<xn-form-container
		:title="formData.templateId ? '编辑Template2' : '增加Template2'"
		:width="700"
		v-model:open="open"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-row :gutter="16">
				<a-col :span="12">
					<a-form-item label="业务ID：" name="transactionId">
						<a-input v-model:value="formData.transactionId" placeholder="请输入业务ID" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="模版名称：" name="templateName">
						<a-input v-model:value="formData.templateName" placeholder="请输入模版名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="模版序号：" name="templateSerial">
						<a-input v-model:value="formData.templateSerial" placeholder="请输入模版序号" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="子模版数量：" name="subtemplateNum">
						<a-input v-model:value="formData.subtemplateNum" placeholder="请输入子模版数量" allow-clear />
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button style="margin-right: 8px" type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button v-if="formData.templateId" type="primary" @click="nextshoru" :loading="submitLoading">子模块</a-button>
		</template>
	</xn-form-container>
	<xn-form-container
		:title="formData.templateId ? '编辑Template2' : '增加Template2'"
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
			<a-button style="margin-right: 8px" type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
			<a-button type="primary"  @click="nextshoru" :loading="submitLoading">下一步</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="template2Form">

	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import template2Api from '@/api/biz/template2Api'

	import ComponentA from '../SubTemplate001/index.vue';
	// 抽屉状态
	const open = ref(false)
	const open1 = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	let formData1ID = ref()
	// 表单数据
	const formData = ref({})
	const formData1 = ref()
	const submitLoading = ref(false)

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
		console.log(formData1.value.templateId)
		formData1ID = formData1.value.templateId
		console.log(formData1ID)
		if (record) {
			let recordData = cloneDeep(record)
			console.log(111111)
			formData.value = Object.assign({}, recordData)
		}
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
	// 关闭抽屉
	const onClose = () => {
		// formRef.value.resetFields()
		formData.value = {}
		// open.value = false
		// open1.value = false
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
				template2Api
					.template2SubmitForm(formDataParam, formDataParam.templateId)
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
		onOpen1
	})
</script>
