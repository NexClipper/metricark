// @ts-nocheck
import React from "react";

import { ResourceComparison } from "components/common/Visualization";

import { area1 as area1Response } from "./data";
import { SimpleCard } from "./shared";
import Utilization from "./Utilization";

// Area 1 types
const booleanResultType = ["controller", "scheduler", "apiserver"];

const booleanLabel = {
  apiserver: {
    true: "Up",
    false: "Down",
  },
};

const simpleResultType = ["pv", "docker"];

const proportionType = [
  "node",
  "namespace",
  "replicaset",
  "deployment",
  "daemonset",
  "statefulset",
  "pod",
];

function getString(data) {
  try {
    return data.result[0].value[1];
  } catch (e) {
    // debugger;
  }
}

function formatNumber(number) {
  return Number(Number(number).toFixed(2));
}

// Area 1 - Cluster Text
export const ClusterTextInfoRenderer: React.FC<{
  dataKey: keyof typeof area1Response.data;
  data: any;
  cardSize: number;
}> = ({ dataKey, data, cardSize }) => {
  if (booleanResultType.includes(dataKey)) {
    const result = getString(data) === "1";
    if (booleanLabel[dataKey]) {
      <SimpleCard title={dataKey} col={cardSize}>
        {booleanLabel[dataKey][result]}
      </SimpleCard>;
    }
    return (
      <SimpleCard title={dataKey} col={cardSize}>
        {result ? "True" : "False"}
      </SimpleCard>
    );
  }

  if (simpleResultType.includes(dataKey)) {
    return (
      <SimpleCard title={dataKey} col={cardSize}>
        {getString(data)}
      </SimpleCard>
    );
  }

  if (proportionType.includes(dataKey)) {
    return (
      <SimpleCard title={dataKey} col={cardSize}>
        {getString(data.active)} / {getString(data.total)}
      </SimpleCard>
    );
  }

  return <SimpleCard title={dataKey}>-</SimpleCard>;
};

// Area 2 - Cluster Utilization
const UTILIZATION_LABEL = {
  mem: "Memory",
  cpu: "CPU",
  pod: "Pod",
};
export const ClusterUtilizationInfoRenderer: React.FC<{
  dataKey: keyof typeof area2Response.data;
  data: any;
  cardSize: number;
}> = ({ dataKey, data, cardSize }) => {
  const _usage = Number(getString(data.usage)) || 0;
  const usage = dataKey === "pod" ? _usage : _usage / 100;
  const label = UTILIZATION_LABEL[dataKey];
  return (
    <SimpleCard title={`Cluster ${label} Utilization (%)`} col={cardSize}>
      <Utilization
        usage={usage}
        gaugeTitle={label}
        isByte={dataKey === "mem"}
        used={formatNumber(getString(data.used))}
        total={formatNumber(getString(data.total))}
      />
    </SimpleCard>
  );
};

// Area 3 - Resource Comparison
const RESOURCE_COMPARISON_CARD_TITLE = {
  pod: {
    cpu: "Pods Top CPU (%)",
    mem: "Pods Top Memory (%)",
  },
  node: {
    cpu: "Node Top CPU (%)",
    mem: "Node Top Memory (%)",
  },
};

const ROW_COUNT_LIMIT = 8;
function clipRows(rows) {
  return rows
    .sort((a, b) => b.value - a.value)
    .filter((_, i) => i < ROW_COUNT_LIMIT);
}

export const ResourceComparisonInfoRenderer: React.FC<{
  parentKey: "pod" | "node";
  dataKey: keyof typeof area3Response.data;
  data: any;
  cardSize: number;
}> = ({ parentKey, dataKey, data, cardSize }) => {
  const resourceData = data[dataKey].result.map((row) => ({
    name:
      row.metric.pod ??
      row.metric.kubernetes_node ??
      row.metric.kubernetes_io_hostname,
    value: formatNumber(row.value[1]),
  }));

  return (
    <SimpleCard
      title={RESOURCE_COMPARISON_CARD_TITLE[parentKey][dataKey]}
      col={cardSize}
    >
      <ResourceComparison resourceData={clipRows(resourceData)} />
    </SimpleCard>
  );
};
