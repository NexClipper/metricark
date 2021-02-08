// @ts-nocheck
import React from "react";

import { ResourceComparisonInfoRenderer } from "../Renderer";

import AreaRequest from "./Request";

const Area3Slice: React.FC<{
  dataKey: "pod" | "node";
  data: any;
}> = ({ dataKey, data }) => {
  return (
    <>
      <ResourceComparisonInfoRenderer
        parentKey={dataKey}
        dataKey="cpu"
        data={data}
        cardSize={1 / 4}
      />
      <ResourceComparisonInfoRenderer
        parentKey={dataKey}
        dataKey="mem"
        data={data}
        cardSize={1 / 4}
      />
    </>
  );
};

const Area3 = () => {
  return (
    <AreaRequest
      areaNumber={3}
      dataKeys={["pod", "node"]}
      component={Area3Slice}
    />
  );
};

export default Area3;
