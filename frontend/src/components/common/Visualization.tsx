// @ts-nocheck
import React from "react";
import { Bar, Gauge } from "@ant-design/charts";

const ticks = [0, 1 / 3, 2 / 3, 1];
const color = ["#F4664A", "#FAAD14", "#30BF78"];
const UtilizationGaugeConfig = {
  range: {
    ticks: [0, 1],
    color: ["l(0) 0:#F4664A 0.5:#FAAD14 1:#30BF78"],
  },
  indicator: {
    pointer: { style: { stroke: "#D0D0D0" } },
    pin: { style: { stroke: "#D0D0D0" } },
  },
  axis: {
    label: {
      formatter: function formatter(v) {
        return Number(v) * 100;
      },
    },
    subTickLine: { count: 3 },
  },
};
const statisticTitle = {
  style({ percent }) {
    return {
      fontSize: "36px",
      lineHeight: 1,
      color:
        percent < ticks[1]
          ? color[0]
          : percent < ticks[2]
          ? color[1]
          : color[2],
    };
  },
};

export const UtilizationGauge: React.FC<{ title: string; value: number }> = ({
  value,
  title,
}) => {
  const statistic = {
    title: statisticTitle,
    content: {
      offsetY: 36,
      style: {
        fontSize: "24px",
        color: "#4B535E",
      },
      formatter: () => title ?? "",
    },
  };
  return (
    <Gauge {...UtilizationGaugeConfig} percent={value} statistic={statistic} />
  );
};

const ResourceComparisonConfig = {
  xField: "value",
  yField: "name",
  seriesField: "name",
  renderer: "svg",
  isGroup: false,
  isStack: true,
  legend: { layout: "horizontal", position: "top-left", reversed: false },
  tooltip: {
    formatter: (datum: { name: string; value: number }) => {
      return { name: datum.name, value: datum.value + "%" };
    },
  },
};

export const ResourceComparison: React.FC = ({ resourceData }) => {
  // const [ref, setRef] = useState(null);
  // const [data, setData] = useState(null);

  // useEffect(() => {
  //   if (resourceData && ref) {
  //     ref.update({
  //       ...ResourceComparisonConfig,
  //       data: resourceData,
  //     })
  //   }
  // }, [resourceData, ref])

  return <Bar {...ResourceComparisonConfig} data={resourceData} />;
};
