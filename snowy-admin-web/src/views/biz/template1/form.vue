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
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="template1Form">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import template1Api from '@/api/biz/template1Api'
	// 抽屉状态
	const open = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)

	// 打开抽屉
	const onOpen = (record) => {
		open.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		open.value = false
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
