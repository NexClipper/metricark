// @ts-nocheck
import { useQuery } from "react-query";

import request from "../../request";

function useDashboard<T extends any>(dataKey: string, areaNumber: number) {
  return useQuery<T>(dataKey, () => request.dashboard(areaNumber), {
    staleTime: Infinity,
    refetchOnWindowFocus: false,
    // refetchInterval: 15 * 1000,
    refetchInterval: 3 * 1000,
    notifyOnChangeProps: ["data"],
  });
}

export default useDashboard;
