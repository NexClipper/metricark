import React from "react";
import { useQuery } from "react-query";
import { Alert, Spin } from "antd";

import request from "../Dashboard/request";
import { SimpleCard } from "../Dashboard/shared";

import { QueryFields } from "./types";

const QueryRequest: React.FC<{
  component: React.FC<{ data: any; field: QueryFields }>;
  field: QueryFields;
}> = ({ component: Component, field }) => {
  // @ts-ignore
  const { isLoading, error, data: response } = useQuery(
    ["kubQuery", field],
    () => request.kubQueryList(field),
    {
      notifyOnChangeProps: ["data"],
    }
  );

  if (error) {
    return (
      <SimpleCard title="">
        <Alert message="Server error" type="error" showIcon />
      </SimpleCard>
    );
  }

  if (isLoading || !response?.data) {
    return (
      <SimpleCard title="">
        <Spin />
      </SimpleCard>
    );
  }

  const { data } = response.data;

  return <Component data={data} field={field} />;
};

export default QueryRequest;
