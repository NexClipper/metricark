let rules = {
  "jsx-a11y/alt-text": "off",
};

let plugins;

const importSortRules = {
  "sort-imports": "off",
  "import/first": "error",
  "import/newline-after-import": "error",
  "import/no-duplicates": "error",
  "simple-import-sort/exports": "off",
  "simple-import-sort/imports": [
    "error",
    {
      groups: [
        // Node.js builtins
        [
          "^(assert|buffer|child_process|cluster|console|constants|crypto|dgram|dns|domain|events|fs|http|https|module|net|os|path|punycode|querystring|readline|repl|stream|string_decoder|sys|timers|tls|tty|url|util|vm|zlib|freelist|v8|process|async_hooks|http2|perf_hooks)(/.*|$)",
        ],
        // Packages. `react` related packages come first
        ["^react", "^@?\\w"],
        // Internal packages
        ["^(@ui|components|pages)(/.*|$)"],
        ["^(store)(/.*|$)", "^(hooks)(/.*|$)", "^(api)(/.*|$)"],
        // Side effect imports
        ["^\\u0000"],
        // Parent imports. Put `..` last
        ["^\\.\\.(?!/?$)", "^\\.\\./?$"],
        // Other relative imports. Put same-folder imports and `.` last
        ["^\\./(?=.*/)(?!/?$)", "^\\.(?!/?$)", "^\\./?$"],
        // env, const, utils
        [
          "^(env|const)(/.*|$)",
          "^(history)(/.*|$)",
          "^(utils)(/.*|$)",
          "^(paths)(/.*|$)",
        ],
        // Style, image imports
        ["^(assets)(/.*|$)", "^.+\\.(s?css|png|jpg|svg)$", "^(style)(/.*|$)"],
      ],
    },
  ],
};

if (process.env.PRECOMMIT) {
  rules = {
    ...rules,
    ...importSortRules,
    "@typescript-eslint/no-unused-vars": "error",
  };
  plugins = ["simple-import-sort", "import"];
}

module.exports = {
  extends: "react-app",
  rules,
  plugins,
};
