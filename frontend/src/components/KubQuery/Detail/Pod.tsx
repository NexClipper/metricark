// @ts-nocheck
import React from "react";
import { useQuery } from "react-query";
import { Area } from "@ant-design/charts";
import { x } from "@xstyled/emotion";
import { Table, Tag } from "antd";
import day from "dayjs";
import { map } from "lodash-es";
import pb from "pretty-bytes";

import { SimpleCard as SimpleCardOriginal } from "components/Dashboard/shared";

import request from "../../../request";

const InfoRow: React.FC<{ title: string; content: React.ReactNode }> = ({
  title,
  content,
}) => {
  return (
    <x.div color="#333" pb="15px">
      <x.div fontWeight="bold">{title}</x.div>
      <x.div color="#111">{content}</x.div>
    </x.div>
  );
};

const SimpleCard = ({ ...props }) => (
  <SimpleCardOriginal col={1} mb="30px" {...props} />
);

const Pod: React.FC<{ data: any }> = ({ data }) => {
  return (
    <x.div>
      <SimpleCard title="Info">
        <x.div row>
          <x.div col={1 / 2}>
            <InfoRow title="Pod Name" content={data.metadata.name} />
            <InfoRow title="Node Name" content={data.spec.nodeName} />
            <InfoRow
              title="Creation Time"
              content={data.metadata.creationTimestamp}
            />
            <InfoRow title="QoS Class" content={data.status.qosClass} />
            <InfoRow
              title="Labels"
              content={
                <>
                  {map(data.metadata.labels, (k, l) => {
                    return <Tag key={k}>{l}</Tag>;
                  })}
                </>
              }
            />
          </x.div>
          <x.div col={1 / 2}>
            <InfoRow title="Namespace" content={data.metadata.name} />
            <InfoRow title="Host IP" content={data.status.hostIP} />
            <InfoRow title="Status" content={data.status.phase} />
            <InfoRow
              title="Owner Kind"
              content={data.metadata.ownerReferences?.[0].kind}
            />
          </x.div>
        </x.div>
      </SimpleCard>
      <SimpleCard title="Containers">
        <Table
          dataSource={data.spec.containers.map((c) => {
            return {
              name: c.name,
              image: c.image,
              env: c.env ? (
                <>
                  {c.env.map((e, i) => (
                    <Tag key={i}>
                      {e.name}:{e.value}
                    </Tag>
                  ))}
                </>
              ) : (
                ""
              ),
              imagePullPolicy: c.imagePullPolicy,
              ports: c.ports ? (
                <>
                  {c.ports.map((e, i) => (
                    <Tag key={i}>
                      {e.protocol}:{e.name}:{e.containerPort}
                    </Tag>
                  ))}
                </>
              ) : (
                ""
              ),
            };
          })}
          columns={[
            {
              title: "Name",
              dataIndex: "name",
              key: "name",
            },
            {
              title: "Image",
              dataIndex: "image",
              key: "image",
            },
            {
              title: "Env",
              dataIndex: "env",
              key: "env",
            },
            {
              title: "Image pull policy",
              dataIndex: "imagePullPolicy",
              key: "imagePullPolicy",
            },
            {
              title: "Ports",
              dataIndex: "ports",
              key: "ports",
            },
          ]}
        />
      </SimpleCard>
      <SimpleCard title="Conditions">
        <Table
          dataSource={data.status.conditions
            .sort((a, b) => {
              return (
                new Date(a.lastTransitionTime) - new Date(b.lastTransitionTime)
              );
            })
            .map((c) => {
              return {
                type: c.type,
                status: c.status,
                reason: c.reason,
                message: c.message,
                lastTransitionTime: c.lastTransitionTime,
              };
            })}
          columns={[
            {
              title: "Type",
              dataIndex: "type",
              key: "type",
            },
            {
              title: "Status",
              dataIndex: "status",
              key: "status",
            },
            {
              title: "Reason",
              dataIndex: "reason",
              key: "reason",
            },
            {
              title: "Message",
              dataIndex: "message",
              key: "message",
            },
            {
              title: "Last transition time",
              dataIndex: "lastTransitionTime",
              key: "transition",
            },
          ]}
        />
      </SimpleCard>
      <SimpleCard title="CPU">
        <CPU name={data.metadata.name} />
      </SimpleCard>
      <SimpleCard title="Memory">
        <Memory name={data.metadata.name} />
      </SimpleCard>
      <SimpleCard title="Volume">
        <Table
          dataSource={
            data.spec.containers[0]?.volumeMounts.map((m) => {
              return {
                name: m.name,
                mountPath: m.mountPath,
                readonly: m.readOnly ? "Y" : "",
              };
            }) ?? []
          }
          columns={[
            {
              title: "Name",
              dataIndex: "name",
              key: "name",
            },
            {
              title: "Mount path",
              dataIndex: "mountPath",
              key: "mountPath",
            },
            {
              title: "Read Only",
              dataIndex: "readonly",
              key: "readonly",
            },
          ]}
        />
      </SimpleCard>
    </x.div>
  );
};

const CPU = ({ name }) => {
  const { data: response } = useQuery<any>(
    "podCpuUsage",
    () => request.podCpuUsage(name),
    {
      staleTime: Infinity,
      refetchOnWindowFocus: false,
      notifyOnChangeProps: ["data"],
    }
  );

  const data = response?.data?.data;

  if (!data) {
    return null;
  }

  const result = data.result[0].values.map((v) => ({
    x: String(v[0]),
    y: Number(v[1]),
  }));

  return (
    <Area
      data={result}
      {...AreaChartConfig}
      yAxis={{
        label: {
          formatter: function formatter(value) {
            return `${Number(value).toFixed(2)}%`;
          },
        },
      }}
      tooltip={{
        formatter: (datum) => {
          return {
            name: day.unix(datum.x).format("HH:mm:ss"),
            value: `${Number(datum.y).toFixed(6)}%`,
          };
        },
        showTitle: false,
      }}
    />
  );
};

const Memory = ({ name }) => {
  const { data: response } = useQuery<any>(
    "podMemUsage",
    () => request.podMemUsage(name),
    {
      staleTime: Infinity,
      refetchOnWindowFocus: false,
      notifyOnChangeProps: ["data"],
    }
  );
  const data = response?.data?.data;

  if (!data) {
    return null;
  }

  const result = data.result[0].values.map((v) => ({
    x: String(v[0]),
    y: Number(v[1]),
  }));

  return (
    <Area
      {...AreaChartConfig}
      data={result}
      yAxis={{
        label: {
          formatter: function formatter(value) {
            return pb(Number(value));
          },
        },
      }}
      tooltip={{
        formatter: (datum) => {
          return {
            name: day.unix(datum.x).format("HH:mm:ss"),
            value: pb(Number(datum.y)),
          };
        },
        showTitle: false,
      }}
    />
  );
};

const AreaChartConfig = {
  xField: "x",
  yField: "y",
  xAxis: {
    label: {
      formatter: function formatter(value) {
        const ret = day.unix(value).format("HH:mm:ss");
        return ret;
      },
    },
  },
  areaStyle: function areaStyle() {
    return { fill: "l(270) 0:#ffffff 0.5:#7ec2f3 1:#1890ff" };
  },
};

export default Pod;
