import React from "react";
import { useQuery } from "react-query";
import { Alert, Spin } from "antd";

import request from "../../request";
import { SimpleCard } from "../Dashboard/shared";

import { QueryFields } from "./types";

const QueryRequest: React.FC<{
  component: React.FC<{ data: any; field: QueryFields }>;
  field: QueryFields;
  type: "list" | "detail";
  name?: string;
}> = ({ component: Component, field, name = "", type }) => {
  // @ts-ignore
  const { isLoading, error, data: response } = useQuery<any>(
    ["kubQuery", `${field}${name}`],
    () => {
      if (type === "list") {
        return request.kubQueryList(field);
      } else if (type === "detail" && name) {
        return request.kubQueryDetail(field, name);
      }
    },
    {
      notifyOnChangeProps: ["data"],
      refetchOnWindowFocus: false,
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
