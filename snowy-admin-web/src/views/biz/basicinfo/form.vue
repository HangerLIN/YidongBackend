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
		</template>
	</xn-form-container>
</template>

<script setup name="projectBasicInfoForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import projectBasicInfoApi from '@/api/biz/projectBasicInfoApi'
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
		onOpen
	})
</script>
