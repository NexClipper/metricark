// @ts-nocheck
import React from "react";
import { x } from "@xstyled/emotion";

import { ClusterTextInfoRenderer } from "../Renderer";

import AreaRequest from "./Request";

const Area1Slice: React.FC<{
  data: any;
  title: string;
  dataKeys: string[];
}> = ({ data, dataKeys, title }) => {
  return (
    <x.div py={10}>
      <x.h3 pl={10}>{title}</x.h3>
      <x.div row>
        {dataKeys.map((k) => {
          const rowData = data[k];
          return (
            <ClusterTextInfoRenderer
              key={k}
              dataKey={k}
              data={rowData}
              cardSize={1 / dataKeys.length}
            />
          );
        })}
      </x.div>
    </x.div>
  );
};

const Area1 = () => {
  return (
    <AreaRequest
      areaNumber={1}
      render={({ data }) => {
        return (
          <>
            <Area1Slice
              data={data}
              dataKeys={[
                "controller",
                "apiserver",
                "scheduler",
                "node",
                "namespace",
                "docker",
              ]}
              title="Kubernetes cluster"
            />
            <Area1Slice
              data={data}
              dataKeys={[
                "daemonset",
                "deployment",
                "replicaset",
                "statefulset",
                "pod",
                "pv",
              ]}
              title="Kubernetes workloads"
            />
          </>
        );
      }}
    />
  );
};

export default Area1;
