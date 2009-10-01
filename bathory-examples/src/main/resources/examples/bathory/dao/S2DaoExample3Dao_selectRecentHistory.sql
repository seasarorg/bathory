SELECT *
FROM emp
WHERE hiredate > PARSEDATETIME(/*targetDate*/'19810131', 'yyyyMMdd')