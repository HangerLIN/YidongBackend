<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="业务ID" name="transactionId">
						<a-input v-model:value="searchFormState.transactionId" placeholder="请输入业务ID" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="模版名称" name="templateName">
						<a-input v-model:value="searchFormState.templateName" placeholder="请输入模版名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="tableRef.refresh()">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table
			ref="tableRef"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.templateId"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('template2Add')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('template2BatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTemplate2"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('template2Edit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['template2Edit', 'template2Delete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTemplate2(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('template2Delete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" :TemplateId="props.TemplateId" @successful="tableRef.refresh()" />
</template>

<script setup name="Template2">
	import { cloneDeep } from 'lodash-es'
	import Form from './form.vue'
	import template2Api from '@/api/biz/template2Api'
	const searchFormState = ref({})
	const searchFormRef = ref()
	const tableRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }

	const props = defineProps({
		TemplateId: {
			type: Number,
			default: 0,
		},
		proId: {
			type: Number,
			default: 0,
		},
	})
	const columns = [
		{
			title: '业务ID',
			dataIndex: 'transactionId'
		},
		{
			title: '模版名称',
			dataIndex: 'templateName'
		},
		{
			title: '模版序号',
			dataIndex: 'templateSerial'
		},
		{
			title: '子模版数量',
			dataIndex: 'subtemplateNum'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['template2Edit', 'template2Delete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: 150
		})
	}
	const selectedRowKeys = ref([])
	// 列表选择配置
	const options = {
		// columns数字类型字段加入 needTotal: true 可以勾选自动算账
		alert: {
			show: true,
			clear: () => {
				selectedRowKeys.value = ref([])
			}
		},
		rowSelection: {
			onChange: (selectedRowKey, selectedRows) => {
				selectedRowKeys.value = selectedRowKey
			}
		}
	}
	const loadData = (parameter) => {
		const searchFormParam = cloneDeep(searchFormState.value)
		parameter.transactionId=props.TemplateId
		console.log(parameter)
		return template2Api.template2Page(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		tableRef.value.refresh(true)
	}
	// 删除
	const deleteTemplate2 = (record) => {
		let params = [
			{
				templateId: record.templateId
			}
		]
		template2Api.template2Delete(params).then(() => {
			tableRef.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTemplate2 = (params) => {
		template2Api.template2Delete(params).then(() => {
			tableRef.value.clearRefreshSelected()
		})
	}
</script>
