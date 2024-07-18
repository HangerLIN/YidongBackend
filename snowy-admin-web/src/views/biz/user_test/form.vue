<template>
	<xn-form-container
		:title="formData.id ? '编辑用户' : '增加用户'"
		:width="700"
		v-model:open="open"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="头像：" name="avatar">
				<a-input v-model:value="formData.avatar" placeholder="请输入头像" allow-clear />
			</a-form-item>
			<a-form-item label="签名：" name="signature">
				<a-input v-model:value="formData.signature" placeholder="请输入签名" allow-clear />
			</a-form-item>
			<a-form-item label="账号：" name="account">
				<a-input v-model:value="formData.account" placeholder="请输入账号" allow-clear />
			</a-form-item>
			<a-form-item label="密码：" name="password">
				<a-input v-model:value="formData.password" placeholder="请输入密码" allow-clear />
			</a-form-item>
			<a-form-item label="姓名：" name="name">
				<a-input v-model:value="formData.name" placeholder="请输入姓名" allow-clear />
			</a-form-item>
			<a-form-item label="昵称：" name="nickname">
				<a-input v-model:value="formData.nickname" placeholder="请输入昵称" allow-clear />
			</a-form-item>
			<a-form-item label="性别：" name="gender">
				<a-input v-model:value="formData.gender" placeholder="请输入性别" allow-clear />
			</a-form-item>
			<a-form-item label="年龄：" name="age">
				<a-input v-model:value="formData.age" placeholder="请输入年龄" allow-clear />
			</a-form-item>
			<a-form-item label="出生日期：" name="birthday">
				<a-input v-model:value="formData.birthday" placeholder="请输入出生日期" allow-clear />
			</a-form-item>
			<a-form-item label="民族：" name="nation">
				<a-input v-model:value="formData.nation" placeholder="请输入民族" allow-clear />
			</a-form-item>
			<a-form-item label="籍贯：" name="nativePlace">
				<a-input v-model:value="formData.nativePlace" placeholder="请输入籍贯" allow-clear />
			</a-form-item>
			<a-form-item label="家庭住址：" name="homeAddress">
				<a-input v-model:value="formData.homeAddress" placeholder="请输入家庭住址" allow-clear />
			</a-form-item>
			<a-form-item label="通信地址：" name="mailingAddress">
				<a-input v-model:value="formData.mailingAddress" placeholder="请输入通信地址" allow-clear />
			</a-form-item>
			<a-form-item label="证件类型：" name="idCardType">
				<a-input v-model:value="formData.idCardType" placeholder="请输入证件类型" allow-clear />
			</a-form-item>
			<a-form-item label="证件号码：" name="idCardNumber">
				<a-input v-model:value="formData.idCardNumber" placeholder="请输入证件号码" allow-clear />
			</a-form-item>
			<a-form-item label="文化程度：" name="cultureLevel">
				<a-input v-model:value="formData.cultureLevel" placeholder="请输入文化程度" allow-clear />
			</a-form-item>
			<a-form-item label="政治面貌：" name="politicalOutlook">
				<a-input v-model:value="formData.politicalOutlook" placeholder="请输入政治面貌" allow-clear />
			</a-form-item>
			<a-form-item label="毕业院校：" name="college">
				<a-input v-model:value="formData.college" placeholder="请输入毕业院校" allow-clear />
			</a-form-item>
			<a-form-item label="学历：" name="education">
				<a-input v-model:value="formData.education" placeholder="请输入学历" allow-clear />
			</a-form-item>
			<a-form-item label="学制：" name="eduLength">
				<a-input v-model:value="formData.eduLength" placeholder="请输入学制" allow-clear />
			</a-form-item>
			<a-form-item label="学位：" name="degree">
				<a-input v-model:value="formData.degree" placeholder="请输入学位" allow-clear />
			</a-form-item>
			<a-form-item label="手机：" name="phone">
				<a-input v-model:value="formData.phone" placeholder="请输入手机" allow-clear />
			</a-form-item>
			<a-form-item label="邮箱：" name="email">
				<a-input v-model:value="formData.email" placeholder="请输入邮箱" allow-clear />
			</a-form-item>
			<a-form-item label="家庭电话：" name="homeTel">
				<a-input v-model:value="formData.homeTel" placeholder="请输入家庭电话" allow-clear />
			</a-form-item>
			<a-form-item label="办公电话：" name="officeTel">
				<a-input v-model:value="formData.officeTel" placeholder="请输入办公电话" allow-clear />
			</a-form-item>
			<a-form-item label="紧急联系人：" name="emergencyContact">
				<a-input v-model:value="formData.emergencyContact" placeholder="请输入紧急联系人" allow-clear />
			</a-form-item>
			<a-form-item label="紧急联系人电话：" name="emergencyPhone">
				<a-input v-model:value="formData.emergencyPhone" placeholder="请输入紧急联系人电话" allow-clear />
			</a-form-item>
			<a-form-item label="紧急联系人地址：" name="emergencyAddress">
				<a-input v-model:value="formData.emergencyAddress" placeholder="请输入紧急联系人地址" allow-clear />
			</a-form-item>
			<a-form-item label="员工编号：" name="empNo">
				<a-input v-model:value="formData.empNo" placeholder="请输入员工编号" allow-clear />
			</a-form-item>
			<a-form-item label="入职日期：" name="entryDate">
				<a-input v-model:value="formData.entryDate" placeholder="请输入入职日期" allow-clear />
			</a-form-item>
			<a-form-item label="机构id：" name="orgId">
				<a-input v-model:value="formData.orgId" placeholder="请输入机构id" allow-clear />
			</a-form-item>
			<a-form-item label="职位id：" name="positionId">
				<a-input v-model:value="formData.positionId" placeholder="请输入职位id" allow-clear />
			</a-form-item>
			<a-form-item label="职级：" name="positionLevel">
				<a-input v-model:value="formData.positionLevel" placeholder="请输入职级" allow-clear />
			</a-form-item>
			<a-form-item label="主管id：" name="directorId">
				<a-input v-model:value="formData.directorId" placeholder="请输入主管id" allow-clear />
			</a-form-item>
			<a-form-item label="兼任信息：" name="positionJson">
				<a-input v-model:value="formData.positionJson" placeholder="请输入兼任信息" allow-clear />
			</a-form-item>
			<a-form-item label="上次登录ip：" name="lastLoginIp">
				<a-input v-model:value="formData.lastLoginIp" placeholder="请输入上次登录ip" allow-clear />
			</a-form-item>
			<a-form-item label="上次登录地点：" name="lastLoginAddress">
				<a-input v-model:value="formData.lastLoginAddress" placeholder="请输入上次登录地点" allow-clear />
			</a-form-item>
			<a-form-item label="上次登录时间：" name="lastLoginTime">
				<a-date-picker v-model:value="formData.lastLoginTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择上次登录时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="上次登录设备：" name="lastLoginDevice">
				<a-input v-model:value="formData.lastLoginDevice" placeholder="请输入上次登录设备" allow-clear />
			</a-form-item>
			<a-form-item label="最新登录ip：" name="latestLoginIp">
				<a-input v-model:value="formData.latestLoginIp" placeholder="请输入最新登录ip" allow-clear />
			</a-form-item>
			<a-form-item label="最新登录地点：" name="latestLoginAddress">
				<a-input v-model:value="formData.latestLoginAddress" placeholder="请输入最新登录地点" allow-clear />
			</a-form-item>
			<a-form-item label="最新登录时间：" name="latestLoginTime">
				<a-date-picker v-model:value="formData.latestLoginTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择最新登录时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="最新登录设备：" name="latestLoginDevice">
				<a-input v-model:value="formData.latestLoginDevice" placeholder="请输入最新登录设备" allow-clear />
			</a-form-item>
			<a-form-item label="用户状态：" name="userStatus">
				<a-input v-model:value="formData.userStatus" placeholder="请输入用户状态" allow-clear />
			</a-form-item>
			<a-form-item label="排序码：" name="sortCode">
				<a-input v-model:value="formData.sortCode" placeholder="请输入排序码" allow-clear />
			</a-form-item>
			<a-form-item label="扩展信息：" name="extJson">
				<a-input v-model:value="formData.extJson" placeholder="请输入扩展信息" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="sysUserForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import sysUserApi from '@/api/biz/sysUserApi'
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
				sysUserApi
					.sysUserSubmitForm(formDataParam, formDataParam.id)
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
