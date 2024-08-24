<template>
	<a-card :bordered="false">
		<s-table
			ref="tableRef"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.transactionId"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('template1Add')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('template1BatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchtemplate1"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('template1Edit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['template1Edit', 'template1Delete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deletetemplate1(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('template1Delete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="tableRef.refresh()" />
</template>

<script setup name="template1">
	import { cloneDeep } from 'lodash-es'
	import Form from './form.vue'
	import template1Api from '@/api/biz/template1Api'
	const tableRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '业务名称',
			dataIndex: 'transactionName'
		},
		{
			title: '业务类型',
			dataIndex: 'transactionType'
		},
		{
			title: '模版数量',
			dataIndex: 'templateNum'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['template1Edit', 'template1Delete'])) {
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
		return template1Api.template1Page(parameter).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		tableRef.value.refresh(true)
	}
	// 删除
	const deletetemplate1 = (record) => {
		let params = [
			{
				transactionId: record.transactionId
			}
		]
		template1Api.template1Delete(params).then(() => {
			tableRef.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchtemplate1 = (params) => {
		template1Api.template1Delete(params).then(() => {
			tableRef.value.clearRefreshSelected()
		})
	}
</script>
