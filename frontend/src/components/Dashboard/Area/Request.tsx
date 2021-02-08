// @ts-nocheck
import React from "react";
import { Alert, Spin } from "antd";

import useDashboard from "../hooks";
import { SimpleCard } from "../shared";

const AreaRequest = ({ areaNumber, component, dataKeys, render }) => {
  const dataKey = `area${areaNumber}`;
  const { data: response, loading, error } = useDashboard(
    `area${areaNumber}`,
    areaNumber
  );

  if (error) {
    return (
      <SimpleCard title={dataKey} key={`area${areaNumber}`}>
        <Alert message="Server error" type="error" showIcon />
      </SimpleCard>
    );
  }

  if (loading || !response) {
    return (
      <SimpleCard title={dataKey} key={`area${areaNumber}`}>
        <Spin />
      </SimpleCard>
    );
  }

  if (render) {
    return <>{render({ data: response.data.data, dataKeys, areaNumber })}</>;
  }

  const Component = component;
  return (
    <>
      {dataKeys.map((dataKey, i) => {
        const key = `area${areaNumber}-${i}`;
        return (
          <Component
            key={key}
            data={response.data.data[dataKey]}
            dataKey={dataKey}
          />
        );
      })}
    </>
  );
};

export default AreaRequest;
