// @ts-nocheck
import React, { useState } from "react";
import { x } from "@xstyled/emotion";
import { Table } from "antd";
import pb from "pretty-bytes";

import { SimpleCard } from "../Dashboard/shared";

const createStringSorter = (k) => (a, b) => a[k].localeCompare(b[k]);
const createNumberSorter = (k) => (a, b) => a[k] - b[k];
const createFilter = (data, k) =>
  Array.from(new Set(data.map((obj) => obj[k])))
    .sort()
    .map((n) => ({ text: n, value: n }));

const createTableData = (data) =>
  Object.keys(data)
    .map((key) => data[key])
    .map((obj) => {
      return {
        name: obj.name,
        node: obj.nodeName,
        namespace: obj.namespace,
        kind: obj.kind[0].kind,
        cpu: Number(obj.metric.cpu?.[1]) || 0,
        memory: Number(obj.metric.mem?.[1]) || 0,
      };
    });

const filterTableData = (objs, filter) =>
  objs.filter((obj) => {
    const ret = [];
    if (filter?.node) {
      ret.push(filter.node.some((n) => n === obj.node));
    }
    if (filter?.namespace) {
      ret.push(filter.namespace.some((n) => n === obj.namespace));
    }
    if (filter?.kind) {
      ret.push(filter.kind.some((n) => n === obj.kind));
    }

    return ret.every((r) => r === true);
  });

const PodList: React.FC<{ data: any }> = ({ data }) => {
  const [page, setPage] = useState(1);
  const [filter, setFilter] = useState(null);
  const tableData = createTableData(data);
  const filteredData = filterTableData(tableData, filter);

  const columns = [
    {
      title: "Name",
      dataIndex: "name",
      onFilter: (value, record) => record.name.includes(value),
      sorter: createStringSorter("name"),
      width: "50%",
    },
    {
      title: "Node",
      dataIndex: "node",
      sorter: createStringSorter("node"),
      filters: createFilter(tableData, "node"),
      width: "10%",
    },
    {
      title: "Namespace",
      dataIndex: "namespace",
      sorter: createStringSorter("namespace"),
      filters: createFilter(tableData, "namespace"),
      width: "10%",
    },
    {
      title: "Kind",
      dataIndex: "kind",
      sorter: createStringSorter("kind"),
      filters: createFilter(tableData, "kind"),
      width: "10%",
    },
    {
      title: "cpu",
      dataIndex: "cpu",
      sorter: createNumberSorter("cpu"),
      render: (record) => {
        return `${(record * 100).toFixed(2)} %`;
      },
      width: "10%",
    },
    {
      title: "memory",
      dataIndex: "memory",
      sorter: createNumberSorter("memory"),
      render: (record) => {
        return pb(record);
      },
      width: "10%",
    },
  ];

  return (
    <x.div p={20}>
      <SimpleCard title="List" col={1}>
        <Table
          columns={columns}
          rowKey={(record) => record.name}
          dataSource={filteredData}
          pagination={{
            current: page,
            pageSize: 10,
            total: filteredData.length,
          }}
          loading={false}
          onChange={(pagination, filter) => {
            setPage(pagination.current);
            setFilter(filter);
          }}
        />
      </SimpleCard>
    </x.div>
  );
};

export default PodList;
