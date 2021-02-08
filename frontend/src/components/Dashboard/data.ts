export const area1 = {
  response_code: 200,
  message: "SUCCESS",
  status: "success",
  data: {
    scheduler: {
      result: [
        {
          metric: {
            container: "kube-scheduler",
          },
          value: [1612167602.238, "1"],
        },
      ],
      resultType: "vector",
    },
    node: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.25, "4"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.264, "4"],
          },
        ],
        resultType: "vector",
      },
    },
    controller: {
      result: [
        {
          metric: {
            container: "kube-controller-manager",
          },
          value: [1612167602.194, "1"],
        },
      ],
      resultType: "vector",
    },
    pod: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.481, "42"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.568, "38"],
          },
        ],
        resultType: "vector",
      },
    },
    pv: {
      result: [
        {
          metric: {},
          value: [1612167602.592, "7"],
        },
      ],
      resultType: "vector",
    },
    statefulset: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.444, "1"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.458, "1"],
          },
        ],
        resultType: "vector",
      },
    },
    namespace: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.286, "12"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.299, "12"],
          },
        ],
        resultType: "vector",
      },
    },
    daemonset: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.339, "5"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.355, "5"],
          },
        ],
        resultType: "vector",
      },
    },
    replicaset: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.412, "17"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.425, "16"],
          },
        ],
        resultType: "vector",
      },
    },
    apiserver: {
      result: [
        {
          metric: {},
          value: [1612167602.218, "1"],
        },
      ],
      resultType: "vector",
    },
    docker: {
      result: [
        {
          metric: {},
          value: [1612167602.32, "45"],
        },
      ],
      resultType: "vector",
    },
    deployment: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167602.377, "14"],
          },
        ],
        resultType: "vector",
      },
      active: {
        result: [
          {
            metric: {},
            value: [1612167602.391, "13"],
          },
        ],
        resultType: "vector",
      },
    },
  },
} as const;

export const area2 = {
  response_code: 200,
  message: "SUCCESS",
  status: "success",
  data: {
    mem: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167681.902, "41908744192"],
          },
        ],
        resultType: "vector",
      },
      usage: {
        result: [
          {
            metric: {},
            value: [1612167681.939, "25.334905191520374"],
          },
        ],
        resultType: "vector",
      },
      used: {
        result: [
          {
            metric: {},
            value: [1612167681.919, "10617540608"],
          },
        ],
        resultType: "vector",
      },
    },
    pod: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167681.967, "440"],
          },
        ],
        resultType: "vector",
      },
      usage: {
        result: [
          {
            metric: {},
            value: [1612167682.016, "0.09545454545454546"],
          },
        ],
        resultType: "vector",
      },
      used: {
        result: [
          {
            metric: {},
            value: [1612167681.983, "42"],
          },
        ],
        resultType: "vector",
      },
    },
    cpu: {
      total: {
        result: [
          {
            metric: {},
            value: [1612167681.818, "22"],
          },
        ],
        resultType: "vector",
      },
      usage: {
        result: [
          {
            metric: {},
            value: [1612167681.861, "13.62552356884599"],
          },
        ],
        resultType: "vector",
      },
      used: {
        result: [
          {
            metric: {},
            value: [1612167681.847, "2.8000000000000003"],
          },
        ],
        resultType: "vector",
      },
    },
  },
} as const;

export const area3 = {
  response_code: 200,
  message: "SUCCESS",
  status: "success",
  data: {
    node: {
      mem: {
        result: [
          {
            metric: {
              kubernetes_io_hostname: "node2",
            },
            value: [1612167715.458, "53.781194888438655"],
          },
          {
            metric: {
              kubernetes_io_hostname: "node4",
            },
            value: [1612167715.458, "27.13466695425043"],
          },
          {
            metric: {
              kubernetes_io_hostname: "node3",
            },
            value: [1612167715.458, "12.333535761161283"],
          },
          {
            metric: {
              kubernetes_io_hostname: "node1",
            },
            value: [1612167715.458, "13.563267673591783"],
          },
        ],
        resultType: "vector",
      },
      cpu: {
        result: [
          {
            metric: {
              pod: "coredns-8686db44f5-dzxbj",
            },
            value: [1612167715.235, "7.295496323529411"],
          },
          {
            metric: {
              pod: "speaker-gq7vm",
            },
            value: [1612167715.235, "13.050781249999998"],
          },
          {
            metric: {
              pod: "provbee-7dc5c759c7-vtv2z",
            },
            value: [1612167715.235, "1.6265869140625"],
          },
          {
            metric: {
              pod: "speaker-dhbgl",
            },
            value: [1612167715.235, "15.269531250000002"],
          },
          {
            metric: {
              pod: "nexclipper-api-586d88d587-f8t22",
            },
            value: [1612167715.235, "35.144805908203125"],
          },
          {
            metric: {
              pod: "controller-57f648cb96-kpdxs",
            },
            value: [1612167715.235, "5.9296875"],
          },
          {
            metric: {
              pod: "speaker-npttm",
            },
            value: [1612167715.235, "8.38671875"],
          },
          {
            metric: {
              pod: "speaker-cgtjp",
            },
            value: [1612167715.235, "22.33203125"],
          },
        ],
        resultType: "vector",
      },
    },
    pod: {
      mem: {
        result: [
          {
            metric: {
              pod: "coredns-8686db44f5-dzxbj",
            },
            value: [1612167715.235, "7.295496323529411"],
          },
          {
            metric: {
              pod: "speaker-gq7vm",
            },
            value: [1612167715.235, "13.050781249999998"],
          },
          {
            metric: {
              pod: "provbee-7dc5c759c7-vtv2z",
            },
            value: [1612167715.235, "1.6265869140625"],
          },
          {
            metric: {
              pod: "speaker-dhbgl",
            },
            value: [1612167715.235, "15.269531250000002"],
          },
          {
            metric: {
              pod: "nexclipper-api-586d88d587-f8t22",
            },
            value: [1612167715.235, "35.144805908203125"],
          },
          {
            metric: {
              pod: "controller-57f648cb96-kpdxs",
            },
            value: [1612167715.235, "5.9296875"],
          },
          {
            metric: {
              pod: "speaker-npttm",
            },
            value: [1612167715.235, "8.38671875"],
          },
          {
            metric: {
              pod: "speaker-cgtjp",
            },
            value: [1612167715.235, "22.33203125"],
          },
        ],
        resultType: "vector",
      },
      cpu: {
        result: [
          {
            metric: {
              pod: "nc-promscale-d599c5c7d-v6vlc",
            },
            value: [1612167715.109, "4.4195286894151895"],
          },
          {
            metric: {
              pod: "etcd-node1",
            },
            value: [1612167715.109, "3.683446200220402"],
          },
          {
            metric: {
              pod: "klevr-agent-4p88w",
            },
            value: [1612167715.109, "0.4549820580870704"],
          },
          {
            metric: {
              pod: "weave-net-ffqvs",
            },
            value: [1612167715.109, "0.3810339412998036"],
          },
          {
            metric: {
              pod: "metricops-54cf55998f-w29dw",
            },
            value: [1612167715.109, "2.254892845023842"],
          },
          {
            metric: {
              pod: "nexclipper-api-586d88d587-f8t22",
            },
            value: [1612167715.109, "0.43824912259589605"],
          },
          {
            metric: {
              pod: "nc-prometheus-node-exporter-bbbzk",
            },
            value: [1612167715.109, "0.0981461181014301"],
          },
          {
            metric: {
              pod: "nc-prometheus-node-exporter-7scpl",
            },
            value: [1612167715.109, "0.1143325228015754"],
          },
          {
            metric: {
              pod: "weave-net-vb8qh",
            },
            value: [1612167715.109, "0.44427573004893645"],
          },
          {
            metric: {
              pod: "speaker-npttm",
            },
            value: [1612167715.109, "0.8147208878971466"],
          },
          {
            metric: {
              pod: "kube-scheduler-node1",
            },
            value: [1612167715.109, "0.5586314909501332"],
          },
          {
            metric: {
              pod: "speaker-gq7vm",
            },
            value: [1612167715.109, "0.9237098798935419"],
          },
          {
            metric: {
              pod: "kubernetes-metrics-scraper-86f6785867-2jkbs",
            },
            value: [1612167715.109, "0.02484728875238893"],
          },
          {
            metric: {
              pod: "nc-promlens-df9c4d7dc-wzgdj",
            },
            value: [1612167715.109, "0.025033946819433814"],
          },
          {
            metric: {
              pod: "nc-prometheus-node-exporter-jjc6z",
            },
            value: [1612167715.109, "0.15682904735306177"],
          },
          {
            metric: {
              pod: "klevr-agent-fmt6s",
            },
            value: [1612167715.109, "0.09886662262931455"],
          },
          {
            metric: {
              pod: "kube-controller-manager-node1",
            },
            value: [1612167715.109, "4.571656890988514"],
          },
          {
            metric: {
              pod: "nc-kube-state-metrics-68958c7fcf-nttff",
            },
            value: [1612167715.109, "0.15215074525542704"],
          },
          {
            metric: {
              pod: "weave-net-xtxwp",
            },
            value: [1612167715.109, "0.41313901116693563"],
          },
          {
            metric: {
              pod: "weave-net-x4c6k",
            },
            value: [1612167715.109, "0.34174414917047347"],
          },
          {
            metric: {
              pod: "nc-timescaledb-0",
            },
            value: [1612167715.109, "13.606554388139202"],
          },
          {
            metric: {
              pod: "speaker-cgtjp",
            },
            value: [1612167715.109, "0.7783462751125639"],
          },
          {
            metric: {
              pod: "kube-proxy-228wd",
            },
            value: [1612167715.109, "0.10729558811573559"],
          },
          {
            metric: {
              pod: "klevr-agent-2jtdl",
            },
            value: [1612167715.109, "0.3938808130721451"],
          },
          {
            metric: {
              pod: "speaker-dhbgl",
            },
            value: [1612167715.109, "0.7377559158494953"],
          },
          {
            metric: {
              pod: "controller-57f648cb96-kpdxs",
            },
            value: [1612167715.109, "0.07186942547109831"],
          },
          {
            metric: {
              pod: "provbee-7dc5c759c7-vtv2z",
            },
            value: [1612167715.109, "0.009685427074890852"],
          },
          {
            metric: {
              pod: "kube-proxy-hjvpp",
            },
            value: [1612167715.109, "0.10036047489823108"],
          },
          {
            metric: {
              pod: "nc-prometheus-server-86997dfb8-mfxv2",
            },
            value: [1612167715.109, "4.921620694674056"],
          },
          {
            metric: {
              pod: "kubernetes-dashboard-84b6b4578b-hgvz6",
            },
            value: [1612167715.109, "0.08800569252502924"],
          },
          {
            metric: {
              pod: "kube-apiserver-node1",
            },
            value: [1612167715.109, "9.723014194665353"],
          },
          {
            metric: {
              pod: "nc-prometheus-pushgateway-5fbd98498-m977b",
            },
            value: [1612167715.109, "0.03562797307787462"],
          },
          {
            metric: {
              pod: "nc-grafana-7fccc6f5bf-wchkd",
            },
            value: [1612167715.109, "0.24911721128867853"],
          },
          {
            metric: {
              pod: "nc-prometheus-node-exporter-66msw",
            },
            value: [1612167715.109, "0.1296257915415737"],
          },
          {
            metric: {
              pod: "nc-prometheus-alertmanager-84d88dd566-qwh27",
            },
            value: [1612167715.109, "0.14328939212314218"],
          },
          {
            metric: {
              pod: "coredns-8686db44f5-dzxbj",
            },
            value: [1612167715.109, "0.7678766214984923"],
          },
          {
            metric: {
              pod: "kube-proxy-6t8kn",
            },
            value: [1612167715.109, "0.08705714661547842"],
          },
          {
            metric: {
              pod: "kube-proxy-5hmx8",
            },
            value: [1612167715.109, "0.08298685029787478"],
          },
        ],
        resultType: "vector",
      },
    },
  },
} as const;
