<template>
	<xn-form-container
		:title="formData.subtemplateId ? '编辑SubTemplate001' : '增加SubTemplate001'"
		:width="700"
		v-model:open="open"
		:destroy-on-close="true"
		@close="onClose"
	>
	<tianbao :column="formData" :cyc="epoch" :subrecord="subrecord" :tempID="tempID"></tianbao>
<!--		<a-form ref="formRef" :model="formData" :rules="formRules" layout="horizontal">-->
<!--			<a-form-item label="模版ID：" name="templateId">-->
<!--				<a-input v-model:value="formData.templateId" placeholder="请输入模版ID" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="子模版序号：" name="subtemplateSerial">-->
<!--				<a-input v-model:value="formData.subtemplateSerial" placeholder="请输入子模版序号" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="子模版名称：" name="subtemplateName">-->
<!--				<a-input v-model:value="formData.subtemplateName" placeholder="请输入子模版名称" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="子模版类型（0:涉及计算，1：不涉及计算，2:可涉及计算也可不涉及）：" name="templateType">-->
<!--				<a-input v-model:value="formData.templateType" placeholder="请输入子模版类型（0:涉及计算，1：不涉及计算，2:可涉及计算也可不涉及）" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="基础信息：" name="basicInformation">-->
<!--				<a-input v-model:value="formData.basicInformation" placeholder="请输入基础信息" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="开始年份公式：" name="startyearEq">-->
<!--				<a-input v-model:value="formData.startyearEq" placeholder="请输入开始年份公式" allow-clear />-->
<!--			</a-form-item>-->
<!--			<a-form-item label="其他年份公式：" name="endyearEq">-->
<!--				<a-input v-model:value="formData.endyearEq" placeholder="请输入其他年份公式" allow-clear />-->
<!--			</a-form-item>-->
<!--		</a-form>-->
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="subtemplate001Form">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import subtemplate001Api from '@/api/biz/subtemplate001Api'
	import Kaizhi from "@/views/biz/basicinfo/kaizhi.vue";
	// import Table from "@/views/biz/basicinfo/table1.vue";
	// import TouziMoney from "@/views/biz/basicinfo/touziMoney.vue";
	import Tianbao from "@/views/biz/basicinfo/tianbao.vue";
	// 抽屉状态
	const open = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	const props = defineProps({
		TemplateId: {
			type: Number,
			default: 0,
		},
		proId: {
			type: Number,
			default: 0,
		},
		cyc: {
			type: Number,
			default: 0,
		},
	})
	const epoch = ref(888)
	const subrecord = ref(888)
	const tempID = ref(888)
	// 打开抽屉
	const onOpen = (record) => {
		epoch.value=props.cyc
		console.log(props.cyc)
		console.log('formData1.value.transactionId')
		console.log('formData1.value.transactionId')
		console.log('formData1.value.transactionId')
		console.log(record)
		tempID.value=props.TemplateId
		formData.value = Object.assign({}, {templateId:props.TemplateId})
		open.value = true
		if (record) {
			console.log(record.subtemplateId)
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
			subrecord.value=record.subtemplateId
			console.log(subrecord.value)
			console.log('formData.transactionId')
		}
	}
	// 关闭抽屉
	const onClose = () => {
		// formRef.value.resetFields()
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
				subtemplate001Api
					.subtemplate001SubmitForm(formDataParam, formDataParam.subtemplateId)
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
